package com.mango.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mango.BuildConfig;
import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.UploadImageBean;
import com.mango.ui.widget.TitleBar;
import com.mango.ui.widget.UploadPictureView;
import com.mango.util.AppUtils;
import com.mango.util.DisplayUtils;
import com.mango.util.FileUtils;
import com.mango.util.SelectorImageLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.functions.Action1;

public class PublishDynamicsActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener,UploadPictureView.OnUploadPictureListener {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_conent_num)
    TextView tvContentNum;
    @Bind(R.id.grid_picture)
    GridLayout gridPicture;
    List<UploadImageBean> pictures = new ArrayList<UploadImageBean>();
    FrameLayout.LayoutParams pictureItemLp;
    int dp5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamics);
        
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.found);
        titleBar.setRightText(R.string.publish);

        int conentMaxLen = AppUtils.getMaxLength(etContent);
        RxTextView.textChanges(etContent).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                tvContentNum.setText(String.format(getString(R.string.input_num), String.valueOf(conentMaxLen - charSequence.toString().length())));
            }
        });

        dp5 = (int) getResources().getDimension(R.dimen.dp_5);
        int width = (int) ((DisplayUtils.screenWidth(this) - getResources().getDimension(R.dimen.dp_15) * 2 - dp5 * 2) / 3);
        pictureItemLp = new FrameLayout.LayoutParams(width, width);

        UploadImageBean addImageBean = new UploadImageBean(UploadImageBean.ADD_BTN);
        pictures.add(pictures.size(), addImageBean);
        setImageView();

        initGalleryFinal();
    }

    private void initGalleryFinal(){
        ThemeConfig theme = new ThemeConfig.Builder()
                .setFabNornalColor(getResources().getColor(R.color.color_ffb900))
                .setFabPressedColor(getResources().getColor(R.color.color_ffb900))
                .setCheckNornalColor(getResources().getColor(R.color.color_efeff4))
                .setCheckSelectedColor(getResources().getColor(R.color.color_ffb900))
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarBgColor(getResources().getColor(R.color.color_ffb900))
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new SelectorImageLoader(), theme)
                .setNoAnimcation(true)
                .setTakePhotoFolder(new File(FileUtils.getEnvPath(this, true, Constants.PICTURE_DIR)))
                .build();
        GalleryFinal.init(coreConfig);
    }

    private void setImageView(){
        gridPicture.removeAllViews();
        for (int i = 0; i < 9 && i < pictures.size(); i++){
            UploadImageBean imageBean = pictures.get(i);
            UploadPictureView pictureView = new UploadPictureView(this);
            pictureView.setOnUploadPictureListener(this);
            pictureView.setImageBean(imageBean);
            GridLayout.LayoutParams gl = new GridLayout.LayoutParams(pictureItemLp);
            gl.topMargin = dp5;
            if(i % 3 == 1){
                gl.leftMargin = dp5;
                gl.rightMargin = dp5;
            }
            gridPicture.addView(pictureView, i, gl);
        }
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                AppUtils.showToast(this, getString(R.string.publish));
                break;
        }
    }

    @Override
    public void onAddPicture() {
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(9 - (pictures.size() - 1))
                .setEnableCamera(true)
                .build();

        GalleryFinal.openGalleryMuti(10, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if(resultList == null || resultList.size() == 0){
                    return;
                }
                for (PhotoInfo photoInfo : resultList){
                    UploadImageBean imageBean = new UploadImageBean(UploadImageBean.READY);
                    imageBean.setLocalPath("file://" + photoInfo.getPhotoPath());
                    pictures.add(pictures.size() - 1, imageBean);
                }
                setImageView();
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
