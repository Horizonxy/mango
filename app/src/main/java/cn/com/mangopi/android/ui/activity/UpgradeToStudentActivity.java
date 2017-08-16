package cn.com.mangopi.android.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.UploadBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.model.data.UploadModel;
import cn.com.mangopi.android.presenter.UpdateRolePresenter;
import cn.com.mangopi.android.presenter.UploadPresenter;
import cn.com.mangopi.android.ui.viewlistener.UpdateRoleListener;
import cn.com.mangopi.android.ui.viewlistener.UploadViewListener;
import cn.com.mangopi.android.ui.widget.RoundImageView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.SelectorImageLoader;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.functions.Action1;

public class UpgradeToStudentActivity extends BaseTitleBarActivity implements UpdateRoleListener,UploadViewListener {

    @Bind(R.id.iv_student_card)
    RoundImageView ivStudentCard;
    @Bind(R.id.tv_student_card)
    TextView tvStudentCard;
    @Bind(R.id.et_real_name)
    TextView etRealName;
    GalleryConfig galleryConfig;
    @Bind(R.id.et_school)
    EditText etSchool;
    @Bind(R.id.et_major)
    EditText etMajor;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    UploadPresenter uploadPresenter;
    MemberBean member;
    RequestBody studentCardImage;
    UpdateRolePresenter updateRolePresenter;
    String studentCardRsurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_to_student);
        member = Application.application.getMember();
        if(member == null){
            finish();
        }
        uploadPresenter = new UploadPresenter(new UploadModel(), this);
        updateRolePresenter = new UpdateRolePresenter(new MemberModel(), this);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.check_your_student_role);

        if(galleryConfig == null) {
            IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(List<String> photoList) {
                    if (photoList != null && photoList.size() > 0) {
                        File file = FileUtils.compressImageFromPath(UpgradeToStudentActivity.this, photoList.get(0));
                        studentCardImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        if(studentCardImage == null){
                            return;
                        }
                        uploadPresenter.upload();

                        Application.application.getImageLoader().displayImage(Constants.FILE_PREFIX + photoList.get(0),
                                ivStudentCard, Application.application.getDefaultOptions());
                        ivStudentCard.setVisibility(View.VISIBLE);
                        tvStudentCard.setVisibility(View.INVISIBLE);
                    } else {
                        ivStudentCard.setVisibility(View.GONE);
                        tvStudentCard.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onError() {
                }
            };
            galleryConfig = new GalleryConfig.Builder()
                    .imageLoader(new SelectorImageLoader())    // ImageLoader 加载框架（必填）
                    .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                    .provider("com.mango.fileprovider")   // provider(必填)
                    .multiSelect(false)                      // 是否多选   默认：false
                    .crop(true, 1, 1, 720, 720)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                    .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                    .filePath(FileUtils.getEnvPath(this, true, Constants.PICTURE_DIR))          // 图片存放路径
                    .build();
        }

    }

    @OnClick(R.id.layout_student_card)
    void clickSetudentCard(View v){
        RxPermissions.getInstance(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if(granted){
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UpgradeToStudentActivity.this);
                } else {
                    AppUtils.showToast(UpgradeToStudentActivity.this, "请在 设置-应用管理 中开启此应用的储存授权。");
                }
            }
        });
    }

    @OnClick(R.id.btn_submit)
    void upgradeClick(View v){
        if(TextUtils.isEmpty(etRealName.getText().toString())){
            AppUtils.showToast(this, "请输入真实姓名");
            return;
        }
        if(TextUtils.isEmpty(etSchool.getText().toString())){
            AppUtils.showToast(this, "请输入学校名称");
            return;
        }
        if(TextUtils.isEmpty(studentCardRsurl)){
            AppUtils.showToast(this, "请选择学生证照片或重传");
            return;
        }
        updateRolePresenter.upgradeStudent();
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
    public void onSuccess(Constants.UserIndentity indentity) {
        ActivityBuilder.startSuccessActivity(this, getString(R.string.check_your_student_role), "信息已提交成功，请等候系统确认。");
        finish();
    }

    @Override
    public Map<String, Object> getUpgradeMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("school_name", etSchool.getText().toString());
        map.put("major", etMajor.getText().toString());
        map.put("student_card_photo_rsurl", studentCardRsurl);
        map.put("lst_sessid", Application.application.getSessId());
        map.put("name", etRealName.getText().toString());
        return map;
    }

    @Override
    public void onSuccess(UploadBean upload) {
        studentCardRsurl = upload.getUrl();
    }

    @Override
    public void onUploadFailure(String message) {
        AppUtils.showToast(this, message);
        studentCardRsurl = "";
    }

    @Override
    public void beforeUpload() {
        AppUtils.showToast(this, "图片正在上传，请稍候");
        btnSubmit.setEnabled(false);
    }

    @Override
    public void afterUpload(boolean success) {
        if(success){
            AppUtils.showToast(this, "图片上传成功");
        } else {
            AppUtils.showToast(this, "图片上传失败");
        }
        btnSubmit.setEnabled(true);
    }

    @Override
    public long getEntityId() {
        return member.getId();
    }

    @Override
    public int getEntityTypeId() {
        return Constants.EntityType.MEMBER.getTypeId();
    }

    @Override
    public RequestBody getFile() {
        return studentCardImage;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(uploadPresenter != null){
            uploadPresenter.onDestroy();
        }
        if(updateRolePresenter != null){
            updateRolePresenter.onDestroy();
        }
    }
}
