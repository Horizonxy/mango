package cn.com.mangopi.android.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseTypeBean;
import cn.com.mangopi.android.model.bean.UploadImageBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.presenter.AddCoursePresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.dialog.ListViewDialog;
import cn.com.mangopi.android.ui.viewlistener.AddCourseLisetener;
import cn.com.mangopi.android.ui.widget.UploadPictureView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.FileUtils;
import rx.functions.Action1;

public class AddCourseActivity extends BaseTitleBarActivity implements AddCourseLisetener, UploadPictureView.OnUploadPictureListener {

    AddCoursePresenter addPresenter;
    List<CourseTypeBean> typeList = new ArrayList<>();
    List<CourseClassifyBean> classifyList = new ArrayList<>();
    @Bind(R.id.tv_class)
    TextView tvClassify;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.tv_each_time)
    TextView tvEachTime;
    List<String> serviceTimeList = new ArrayList<>();
    List<String> eachTimeList = new ArrayList<>();
    @Bind(R.id.et_course_title)
    EditText etTitle;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.layout_classify)
    View layoutClassify;
    @Bind(R.id.layout_type)
    View layoutType;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.grid_material)
    GridLayout gridMaterial;
    List<UploadImageBean> materials = new ArrayList<UploadImageBean>();
    FrameLayout.LayoutParams materialItemLp;
    int dp5;
    IHandlerCallBack iHandlerCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        serviceTimeList.add("一次");
        serviceTimeList.add("二次");
        serviceTimeList.add("三次");

        eachTimeList.add("每次约1小时");
        eachTimeList.add("每次约2小时");
        eachTimeList.add("每次约3小时");
        eachTimeList.add("每次约4小时");
        eachTimeList.add("每次约8小时");
        eachTimeList.add("每次约12小时");

        initView();
        addPresenter = new AddCoursePresenter(new CourseModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.add_course);

        tvServiceTime.setText(serviceTimeList.get(0));
        tvEachTime.setText(eachTimeList.get(0));

        dp5 = (int) getResources().getDimension(R.dimen.dp_5);
        int width = (int) ((DisplayUtils.screenWidth(this) - getResources().getDimension(R.dimen.dp_15) * 2 - dp5 * 2) / 3);
        materialItemLp = new FrameLayout.LayoutParams(width, width);

        UploadImageBean addImageBean = new UploadImageBean(UploadImageBean.ADD_BTN);
        UploadPictureView pictureView = new UploadPictureView(this);
        pictureView.setOnUploadPictureListener(this);
        pictureView.setImageBean(addImageBean);
        GridLayout.LayoutParams gl = new GridLayout.LayoutParams(materialItemLp);
        gl.topMargin = dp5;
        gridMaterial.addView(pictureView, gl);
    }

    @OnClick(R.id.tv_class)
    void onClassifyClick(View v){
        if(classifyList.size() == 0){
            addPresenter.getClassify();
        } else {
            showClassifyList(classifyList);
        }
    }

    private void showClassifyList(List<CourseClassifyBean> classifyList){
        if(classifyList.size() > 0) {
            ListViewDialog<CourseClassifyBean> classifyDialog = new ListViewDialog<CourseClassifyBean>("选择课程分类", classifyList) {
                @Override
                public void fiddData(BaseAdapterHelper helper, CourseClassifyBean item) {
                    helper.setText(R.id.tv_text, item.getClassify_name());
                }
            };
            classifyDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<CourseClassifyBean>() {
                @Override
                public void onItemClicked(CourseClassifyBean data) {
                    tvClassify.setText(data.getClassify_name());
                    tvClassify.setTag(data);
                }
            });
            classifyDialog.show(this, (CourseClassifyBean) tvClassify.getTag());
        } else {
            onFailure("无课程分类");
        }
    }

    @OnClick(R.id.tv_type)
    void onTypeClick(View v){
        if(typeList.size() == 0){
            addPresenter.getTypeList();
        } else {
            showTypeList(typeList);
        }
    }

    private void showTypeList(List<CourseTypeBean> typeList){
        if(typeList.size() > 0) {
            ListViewDialog<CourseTypeBean> typeDialog = new ListViewDialog<CourseTypeBean>("选择课程类型", typeList) {
                @Override
                public void fiddData(BaseAdapterHelper helper, CourseTypeBean item) {
                    helper.setText(R.id.tv_text, item.getType() + "(" + item.getMethod() + ")");
                }
            };
            typeDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<CourseTypeBean>() {
                @Override
                public void onItemClicked(CourseTypeBean data) {
                    tvType.setText(data.getType() + "(" + data.getMethod() + ")");
                    tvType.setTag(data);
                    if(data.getSale_price() != null) {
                        tvPrice.setText(getResources().getString(R.string.rmb) + data.getSale_price().toString());
                    }
                }
            });
            typeDialog.show(this, (CourseTypeBean) tvType.getTag());
        } else {
            onFailure("无课程类型");
        }
    }

    @OnClick(R.id.btn_save)
    void onSaveClick(View v){
        if(TextUtils.isEmpty(etTitle.getText())){
            AppUtils.showToast(this, "请输入课程标题");
            return;
        }
        if(tvType.getTag() == null){
            AppUtils.showToast(this, "请选择课程分类");
            return;
        }
        if(tvClassify.getTag() == null){
            AppUtils.showToast(this, "请选择课程类型");
            return;
        }
        if(TextUtils.isEmpty(tvServiceTime.getText()) || TextUtils.isEmpty(tvEachTime.getText())){
            AppUtils.showToast(this, "请选择服务时长");
            return;
        }
        if(TextUtils.isEmpty(etContent.getText())){
            AppUtils.showToast(this, "请输入课程内容");
            return;
        }
        for (int i = 0; i < materials.size(); i++){
            UploadImageBean uploadImage = materials.get(i);
            if(uploadImage.getUploadBean() == null || uploadImage.getType() != UploadImageBean.UPLOADED){
                AppUtils.showToast(this, "文件正在上传，请稍等");
                return;
            }
        }
        addPresenter.addCourse();
    }

    @OnClick(R.id.tv_each_time)
    void onEachTimeClick(View v){
        String data = (String) tvEachTime.getTag();
        ListViewDialog<String> eachDialog = new ListViewDialog<String>("服务次数", eachTimeList) {
            @Override
            public void fiddData(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_text, item);
            }

        };
        eachDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<String>() {
            @Override
            public void onItemClicked(String data) {
                tvEachTime.setText(data);
                tvEachTime.setTag(data);
            }
        });
        eachDialog.show(this, data == null ? eachTimeList.get(0) : data);
    }

    @OnClick(R.id.tv_service_time)
    void onServiceTimeClick(View v){
        String data = (String) tvServiceTime.getTag();
        ListViewDialog<String> serviceDialog = new ListViewDialog<String>("服务时长", serviceTimeList) {
            @Override
            public void fiddData(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_text, item);
            }
        };
        serviceDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<String>() {
            @Override
            public void onItemClicked(String data) {
                tvServiceTime.setText(data);
                tvServiceTime.setTag(data);
            }
        });
        serviceDialog.show(this, data == null ? serviceTimeList.get(0) : data);
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
    public void onClassifySuccess(List<CourseClassifyBean> classifyList) {
        this.classifyList.clear();
        this.classifyList.addAll(classifyList);
        showClassifyList(this.classifyList);
    }

    @Override
    public void onTypeSuccess(List<CourseTypeBean> typeList) {
        this.typeList.clear();
        this.typeList.addAll(typeList);
        showTypeList(typeList);
    }

    @Override
    public void onAddCourseSuccess(Object object) {
        if(object != null) {
            AppUtils.showToast(this, "保存课程成功，请耐心等待管理人员的审核");
            finish();
        }
    }

    @Override
    public Map<String, Object> getAddMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("course_title", etTitle.getText().toString());
        map.put("type_id", ((CourseTypeBean)tvType.getTag()).getId());
        map.put("service_time", tvServiceTime.getText().toString());
        map.put("each_time", tvEachTime.getText().toString());
        map.put("course_content", etContent.getText().toString());
        map.put("material_rsurls", new String[]{});
        map.put("class_id", ((CourseClassifyBean)tvClassify.getTag()).getId());
        map.put("city", "");
        return map;
    }

    @Override
    protected void onDestroy() {
        if(addPresenter != null){
            addPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onAddPicture() {
        RxPermissions.getInstance(this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if (granted) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/msword;application/vnd.openxmlformats-officedocument.wordprocessingml.document;" +
                            "application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;" +
                            "application/vnd.ms-powerpoint;application/vnd.openxmlformats-officedocument.presentationml.presentation;" +
                            "text/plain;application/pdf;image/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, Constants.REQ_FILE);
                } else {
                    AppUtils.showToast(AddCourseActivity.this, "请在 设置-应用管理 中开启此应用的储存授权。");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == Constants.REQ_FILE && data != null){
                Uri uri = data.getData();
                String path = FileUtils.getPath(this, uri);
                Logger.e("path: " + path);

                UploadImageBean imageBean = new UploadImageBean(UploadImageBean.READY);
                imageBean.setLocalPath(path);
                materials.add(imageBean);
                setImageView();
            }
        }
    }

    @Override
    public void onDelView(UploadImageBean uploadImage) {
        int index = 0;
        for (int i = 0; i < materials.size(); i++){
            if(materials.get(i) == uploadImage){
                index = i;
                break;
            }
        }
        if(materials.remove(uploadImage)) {
            gridMaterial.removeViewAt(index);
        }
    }

    private void setImageView() {
        for (int i = gridMaterial.getChildCount() == 0 ? 0 : (gridMaterial.getChildCount() - 1); i < materials.size(); i++) {
            UploadImageBean imageBean = materials.get(i);
            UploadPictureView pictureView = new UploadPictureView(this);
            pictureView.setOnUploadPictureListener(this);
            pictureView.setImageBean(imageBean);
            GridLayout.LayoutParams gl = new GridLayout.LayoutParams(materialItemLp);
            gl.topMargin = dp5;
            if (i % 3 == 0 || i % 3 == 1) {
                gl.rightMargin = dp5;
            }
            gridMaterial.addView(pictureView, i, gl);
        }
        if(gridMaterial.getChildCount() > 9){
            gridMaterial.getChildAt(9).setVisibility(View.GONE);
        } else {
            gridMaterial.getChildAt(gridMaterial.getChildCount() - 1).setVisibility(View.VISIBLE);
        }
    }
}
