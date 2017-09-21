package cn.com.mangopi.android.ui.activity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CommunityClassifyBean;
import cn.com.mangopi.android.model.bean.CommunityTypeBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.UploadBean;
import cn.com.mangopi.android.model.data.CommunityModel;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.model.data.UploadModel;
import cn.com.mangopi.android.presenter.CommunityPresenter;
import cn.com.mangopi.android.presenter.UpdateRolePresenter;
import cn.com.mangopi.android.presenter.UploadPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.dialog.ListViewDialog;
import cn.com.mangopi.android.ui.viewlistener.CommunityListener;
import cn.com.mangopi.android.ui.viewlistener.UpdateRoleListener;
import cn.com.mangopi.android.ui.viewlistener.UploadViewListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.SelectorImageLoader;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.functions.Action1;

public class UpgradeToCommunityActivity extends BaseTitleBarActivity implements UpdateRoleListener,UploadViewListener, CommunityListener {

    @Bind(R.id.et_community_name)
    EditText etCommunityName;
    @Bind(R.id.et_city)
    EditText etCity;
    @Bind(R.id.et_community_school)
    EditText etCommunitySchool;
    @Bind(R.id.et_community_contacts)
    TextView etCommunityContacts;
    @Bind(R.id.tv_community_type)
    TextView tvCommunityType;
    @Bind(R.id.tv_community_classify)
    TextView tvCommunityClassify;
    @Bind(R.id.tv_community_desc)
    TextView tvCommunityDesc;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.iv_camera)
    ImageView ivCamera;
    @Bind(R.id.tv_logo_tip)
    TextView tvLogoTip;
    @Bind(R.id.line_type)
    View lineType;
    @Bind(R.id.line_classify)
    View lineClassify;

    GalleryConfig galleryConfig;
    UploadPresenter uploadPresenter;
    MemberBean member;
    RequestBody communityLogoImage;
    UpdateRolePresenter updateRolePresenter;
    String communityLogoRsurl;
    CommunityPresenter communityPresenter;
    List<CommunityTypeBean> typeList = new ArrayList<CommunityTypeBean>();
    List<CommunityClassifyBean> classifyList = new ArrayList<CommunityClassifyBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_to_community);
        Bus.getDefault().register(this);

        member = Application.application.getMember();
        if(member == null){
            finish();
        }
        uploadPresenter = new UploadPresenter(new UploadModel(), this);
        updateRolePresenter = new UpdateRolePresenter(new MemberModel(), this);
        initView();

        communityPresenter = new CommunityPresenter(new CommunityModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.check_community_role);

        if(galleryConfig == null) {
            IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(List<String> photoList) {
                    if (photoList != null && photoList.size() > 0) {
                        File file = FileUtils.compressImageFromPath(UpgradeToCommunityActivity.this, photoList.get(0));
                        communityLogoImage = RequestBody.create(MediaType.parse(Constants.FORM_DATA), file);
                        if(communityLogoImage == null){
                            return;
                        }
                        uploadPresenter.upload();

                        Application.application.getImageLoader().displayImage(Constants.FILE_PREFIX + photoList.get(0),
                                ivLogo, Application.application.getDefaultOptions());
                        ivLogo.setVisibility(View.VISIBLE);
                        ivCamera.setVisibility(View.GONE);
                        tvLogoTip.setVisibility(View.GONE);
                    } else {
                        ivLogo.setVisibility(View.GONE);
                        ivCamera.setVisibility(View.VISIBLE);
                        tvLogoTip.setVisibility(View.VISIBLE);
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
                    .provider(getPackageName() + ".fileprovider")   // provider(必填)
                    .multiSelect(false)                      // 是否多选   默认：false
                    .crop(true, 1, 1, 720, 720)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                    .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                    .filePath(FileUtils.getEnvPath(this, true, Constants.PICTURE_DIR))          // 图片存放路径
                    .build();
        }
    }

    @OnClick(R.id.layout_logo)
    void clickCommunityLogo(View v){
        RxPermissions.getInstance(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean granted) {
                if(granted){
                    GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UpgradeToCommunityActivity.this);
                } else {
                    AppUtils.showToast(UpgradeToCommunityActivity.this, "请在 设置-应用管理 中开启此应用的储存授权。");
                }
            }
        });
    }

    @OnClick(R.id.tv_community_desc)
    void clickCommunityDesc(View v){
        ActivityBuilder.startInputMessageActivity(this, "社团介绍", getResources().getString(R.string.sure_submit), "community_desc", 100, tvCommunityDesc.getText().toString());
    }


    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event){
        String type = event.getType();
        if("community_desc".equals(type)){
            tvCommunityDesc.setText(event.getContent());
        }
    }

    @OnClick(R.id.btn_submit)
    void upgradeClick(View v){
        if(TextUtils.isEmpty(tvCommunityType.getText().toString())){
            AppUtils.showToast(this, "请选择社团类型");
            return;
        }
        if(TextUtils.isEmpty(etCity.getText().toString())){
            AppUtils.showToast(this, "请输入城市");
            return;
        }
        if(TextUtils.isEmpty(etCommunityName.getText().toString())){
            AppUtils.showToast(this, "请输入社团名称");
            return;
        }
        if(TextUtils.isEmpty(tvCommunityDesc.getText().toString())){
            AppUtils.showToast(this, "请输入详细介绍");
            return;
        }
        if(TextUtils.isEmpty(etCommunityContacts.getText().toString())){
            AppUtils.showToast(this, "请输入社团联系人");
            return;
        }
        if(TextUtils.isEmpty(communityLogoRsurl)){
            AppUtils.showToast(this, "请选择社团形象图或重传");
            return;
        }
        updateRolePresenter.upgradeCommunity();
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
        ActivityBuilder.startSuccessActivity(this, getString(R.string.check_community_role), "信息已提交成功，请等候系统确认。");
        finish();
    }

    @Override
    public Map<String, Object> getUpgradeMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("community_type", tvCommunityType.getText().toString());
        map.put("city", etCity.getText().toString());
        map.put("community_name", etCommunityName.getText().toString());
        map.put("place_school", etCommunitySchool.getText().toString());
        map.put("community_classify", tvCommunityClassify.getText().toString());
        map.put("community_intro", tvCommunityDesc.getText().toString());
        map.put("logo_rsurl", communityLogoRsurl);
        map.put("lst_sessid", Application.application.getSessId());
        return map;
    }

    @Override
    public void onSuccess(UploadBean upload) {
        communityLogoRsurl = upload.getUrl();
    }

    @Override
    public void onUploadFailure(String message) {
        AppUtils.showToast(this, message);
        communityLogoRsurl = "";
    }

    @Override
    public void beforeUpload() {
        AppUtils.showToast(this, R.string.picture_uploading);
        btnSubmit.setEnabled(false);
    }

    @Override
    public void afterUpload(boolean success) {
        if(success){
            AppUtils.showToast(this, R.string.picture_upload_success);
        } else {
            AppUtils.showToast(this, R.string.picture_upload_failure);
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
        return communityLogoImage;
    }

    @Override
    protected void onDestroy() {
        Bus.getDefault().unregister(this);
        super.onDestroy();
        if(uploadPresenter != null){
            uploadPresenter.onDestroy();
        }
        if(updateRolePresenter != null){
            updateRolePresenter.onDestroy();
        }
    }

    @Override
    public void onTypeListSuccess(List<CommunityTypeBean> communityTypeList) {
        this.typeList.clear();
        this.typeList.addAll(communityTypeList);
        showTypeList(typeList);
    }

    @OnClick(R.id.tv_community_type)
    void onTypeClick(View v){
        if(typeList.size() == 0){
            communityPresenter.getTypeList();
        } else {
            showTypeList(typeList);
        }
    }

    private void showTypeList(List<CommunityTypeBean> typeList){
        if(typeList.size() > 0) {
            ListViewDialog<CommunityTypeBean> typeListDialog = new ListViewDialog<CommunityTypeBean>("选择社团类型", typeList) {
                @Override
                public void fiddData(BaseAdapterHelper helper, CommunityTypeBean item) {
                    helper.setText(R.id.tv_text, item.getType_name());
                }
            };
            typeListDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<CommunityTypeBean>() {
                @Override
                public void onItemClicked(CommunityTypeBean data) {
                    tvCommunityType.setText(data.getType_name());
                    tvCommunityType.setTag(data);
                }
            });
            typeListDialog.show(this, (CommunityTypeBean) tvCommunityType.getTag());
        } else {
            onFailure("无社团类型");
        }
    }

    @Override
    public void onClassifyListSuccess(List<CommunityClassifyBean> communityClassifyList) {
        this.classifyList.clear();
        this.classifyList.addAll(communityClassifyList);
        showClassifyList(classifyList);
    }

    @OnClick(R.id.tv_community_classify)
    void onClassifyClick(View v){
        if(classifyList.size() == 0){
            communityPresenter.getClassifyList();
        } else {
            showClassifyList(classifyList);
        }
    }

    private void showClassifyList(List<CommunityClassifyBean> classifyList){
        if(classifyList.size() > 0) {
            ListViewDialog<CommunityClassifyBean> classifyDialog = new ListViewDialog<CommunityClassifyBean>("选择社团分类", classifyList) {
                @Override
                public void fiddData(BaseAdapterHelper helper, CommunityClassifyBean item) {
                    helper.setText(R.id.tv_text, item.getClassic_name());
                }
            };
            classifyDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<CommunityClassifyBean>() {
                @Override
                public void onItemClicked(CommunityClassifyBean data) {
                    tvCommunityClassify.setText(data.getClassic_name());
                    tvCommunityClassify.setTag(data);
                }
            });
            classifyDialog.show(this, (CommunityClassifyBean) tvCommunityClassify.getTag());
        } else {
            onFailure("无社团分类");
        }
    }
}
