package cn.com.mangopi.android.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.mcxiaoke.bus.Bus;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.UploadImageBean;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.presenter.AddTrendPresenter;
import cn.com.mangopi.android.ui.viewlistener.AddTrendListener;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.ui.widget.UploadPictureView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.SelectorImageLoader;
import rx.functions.Action1;

public class PublishDynamicsActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener, UploadPictureView.OnUploadPictureListener, AddTrendListener {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_conent_num)
    TextView tvContentNum;
    @Bind(R.id.grid_picture)
    GridLayout gridPicture;
    List<UploadImageBean> pictures = new ArrayList<UploadImageBean>();
    FrameLayout.LayoutParams pictureItemLp;
    int dp5;
    AddTrendPresenter presenter;
    IHandlerCallBack iHandlerCallBack;
    final int COLUMN_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamics);

        presenter = new AddTrendPresenter(new TrendModel(), this);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.found);
        titleBar.setRightText(R.string.publish);
        titleBar.setOnTitleBarClickListener(this);

        int conentMaxLen = AppUtils.getMaxLength(etContent);
        RxTextView.textChanges(etContent).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                tvContentNum.setText(String.format(getString(R.string.input_num), String.valueOf(conentMaxLen - charSequence.toString().length())));
            }
        });

        dp5 = (int) getResources().getDimension(R.dimen.dp_5);
        int width = (int) ((DisplayUtils.screenWidth(this) - getResources().getDimension(R.dimen.dp_15) * 2 - dp5 * (COLUMN_COUNT - 1)) / COLUMN_COUNT);
        pictureItemLp = new FrameLayout.LayoutParams(width, width);

        UploadImageBean addImageBean = new UploadImageBean(UploadImageBean.ADD_BTN);
        UploadPictureView pictureView = new UploadPictureView(this);
        pictureView.setAddIconResId(R.drawable.add_pic);
        pictureView.setOnUploadPictureListener(this);
        pictureView.setImageBean(addImageBean);
        GridLayout.LayoutParams gl = new GridLayout.LayoutParams(pictureItemLp);
        gl.topMargin = dp5;
        gridPicture.addView(pictureView, gl);
    }

    private void setImageView() {
        for (int i = gridPicture.getChildCount() == 0 ? 0 : (gridPicture.getChildCount() - 1); i < pictures.size(); i++) {
            UploadImageBean imageBean = pictures.get(i);
            UploadPictureView pictureView = new UploadPictureView(this);
            pictureView.setAddIconResId(R.drawable.add_pic);
            pictureView.setOnUploadPictureListener(this);
            pictureView.setImageBean(imageBean);
            GridLayout.LayoutParams gl = new GridLayout.LayoutParams(pictureItemLp);
            gl.topMargin = dp5;
            if (i % COLUMN_COUNT != ((COLUMN_COUNT - 1))) {
                gl.rightMargin = dp5;
            }
            gridPicture.addView(pictureView, i, gl);
        }
        if(gridPicture.getChildCount() > 9){
            gridPicture.getChildAt(9).setVisibility(View.GONE);
        } else {
            gridPicture.getChildAt(gridPicture.getChildCount() - 1).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                for (int i = 0; i < pictures.size(); i++){
                    UploadImageBean uploadImage = pictures.get(i);
                    if(uploadImage.getUploadBean() == null || uploadImage.getType() != UploadImageBean.UPLOADED){
                        AppUtils.showToast(this, "图片正在上传，请稍等");
                        return;
                    }
                }
                if(TextUtils.isEmpty(etContent.getText().toString())){
                    AppUtils.showToast(this, "请输入动态内容");
                    return;
                }
                presenter.addTrend();
                break;
        }
    }

    @Override
    public void onAddPicture() {
        if (iHandlerCallBack == null) {
            iHandlerCallBack = new IHandlerCallBack() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(List<String> photoList) {
                    if (photoList == null || photoList.size() == 0) {
                        return;
                    }
                    for (String photoInfo : photoList) {
                        UploadImageBean imageBean = new UploadImageBean(UploadImageBean.READY);
                        imageBean.setLocalPath(photoInfo);
                        pictures.add(imageBean);
                    }
                    setImageView();
                }

                @Override
                public void onCancel() {}

                @Override
                public void onFinish() {}

                @Override
                public void onError() {}
            };
        }
        GalleryConfig galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new SelectorImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider(getPackageName() + ".fileprovider")   // provider(必填)
                .multiSelect(true, 9 - pictures.size())                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(FileUtils.getEnvPath(this, true, Constants.PICTURE_DIR))          // 图片存放路径
                .build();

        RxPermissions.getInstance(this).requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if(permission.name.equals(Manifest.permission.CAMERA)){
                    if(!permission.granted) {
                        AppUtils.showToast(PublishDynamicsActivity.this, getString(R.string.permission_camera));
                    }
                } else if(permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(PublishDynamicsActivity.this);
                } else {
                    AppUtils.showToast(PublishDynamicsActivity.this, getString(R.string.permission_storage));
                }
            }
        });
    }

    @Override
    public void onDelView(UploadImageBean uploadImage) {
        int index = 0;
        for (int i = 0; i < pictures.size(); i++){
            if(pictures.get(i) == uploadImage){
                index = i;
                break;
            }
        }
        if(pictures.remove(uploadImage)) {
            gridPicture.removeViewAt(index);
        }
        if(gridPicture.getChildCount() > 9){
            gridPicture.getChildAt(9).setVisibility(View.GONE);
        } else {
            gridPicture.getChildAt(gridPicture.getChildCount() - 1).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onSuccess() {
//        BusEvent.RefreshTrendListEvent event = new BusEvent.RefreshTrendListEvent();
//        Bus.getDefault().postSticky(event);
        AppUtils.showToast(this, "动态已发布，请耐心等待管理人员的审核");
        finish();
    }

    @Override
    public String getContent() {
        return etContent.getText().toString();
    }

    @Override
    public List<String> getPics() {
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < pictures.size(); i++){
            UploadImageBean uploadImage = pictures.get(i);
            urls.add(uploadImage.getUploadBean().getUrl());
        }
        return urls;
    }

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
    }
}
