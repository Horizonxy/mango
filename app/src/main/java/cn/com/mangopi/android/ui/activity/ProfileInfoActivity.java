package cn.com.mangopi.android.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
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
import cn.com.mangopi.android.presenter.SettingMemberPresenter;
import cn.com.mangopi.android.presenter.UploadPresenter;
import cn.com.mangopi.android.ui.viewlistener.ProfileSettingListener;
import cn.com.mangopi.android.ui.viewlistener.UploadViewListener;
import cn.com.mangopi.android.ui.widget.MangoDateItemPicker;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DialogUtil;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.MangoUtils;
import cn.com.mangopi.android.util.SelectorImageLoader;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action1;

public class ProfileInfoActivity extends BaseTitleBarActivity implements ProfileSettingListener, UploadViewListener {

    MemberBean member;
    @Bind(R.id.layout_avatar)
    View layoutAvatar;
    @Bind(R.id.tv_avatar)
    TextView tvAvatar;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.layout_nick_name)
    View layoutNickName;
    TextView tvNickName;
    @Bind(R.id.layout_sign)
    View layoutSign;
    TextView tvSign;
    @Bind(R.id.layout_real_name)
    View layoutRealName;
    TextView tvRealName;
    @Bind(R.id.layout_gender)
    View layoutGender;
    TextView tvGender;
    @Bind(R.id.layout_birthday)
    View layoutBirthday;
    TextView tvBirthday;
    @Bind(R.id.layout_school)
    View layoutSchool;
    TextView tvSchool;
    @Bind(R.id.layout_professional)
    View layoutProfessional;
    TextView tvProfessional;
    @Bind(R.id.layout_enter_date)
    View layoutEnterDate;
    TextView tvEnterDate;
    @Bind(R.id.layout_project)
    View layoutProject;
    TextView tvProject;
    @Bind(R.id.layout_works)
    View layoutWorks;
    TextView tvWorks;
    @Bind(R.id.layout_cv)
    View layoutCv;
    TextView tvCv;
    @Bind(R.id.layout_evaluation)
    View layoutEvaluation;
    TextView tvEvaluation;
    @Bind(R.id.layout_qq)
    View layoutQQ;
    TextView tvQQ;
    @Bind(R.id.layout_wechat)
    View layoutWechat;
    TextView tvWechat;
    @Bind(R.id.layout_phone)
    View layoutPhone;
    TextView tvPhone;
    @Bind(R.id.layout_email)
    View layoutEmail;
    TextView tvEmail;
    SettingMemberPresenter presenter;
    MultipartBody.Part memberAvatar;
    UploadPresenter uploadPresenter;
    UploadBean uploadBean;
    GalleryConfig galleryConfig;
    int birthYear = 1990, birthMonth = 1, birthDay = 1;
    Date newBirthday;
    int enterSchoolYear = 2010, enterSchoolMonth = 9, enterSchoolDay = 1;
    Date newEnterSchool;

    int gender;
    int selectedGender = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        member = Application.application.getMember();
        if(member == null){
            finish();
        }

        initView();
        Bus.getDefault().register(this);

        fillData(member);
        presenter = new SettingMemberPresenter(new MemberModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.profile_info);

        ((TextView)layoutNickName.findViewById(R.id.tv_left)).setText("昵称：");
        tvNickName = (TextView) layoutNickName.findViewById(R.id.tv_right);
        ((TextView)layoutSign.findViewById(R.id.tv_left)).setText("签名：");
        tvSign = (TextView) layoutSign.findViewById(R.id.tv_right);
        ((TextView)layoutRealName.findViewById(R.id.tv_left)).setText("真实姓名：");
        tvRealName = (TextView) layoutRealName.findViewById(R.id.tv_right);
        ((TextView)layoutGender.findViewById(R.id.tv_left)).setText("性别：");
        tvGender = (TextView) layoutGender.findViewById(R.id.tv_right);
        ((TextView)layoutBirthday.findViewById(R.id.tv_left)).setText("生日：");
        tvBirthday = (TextView) layoutBirthday.findViewById(R.id.tv_right);
        ((TextView)layoutSchool.findViewById(R.id.tv_left)).setText("学校：");
        tvSchool = (TextView) layoutSchool.findViewById(R.id.tv_right);
        ((TextView)layoutProfessional.findViewById(R.id.tv_left)).setText("专业：");
        tvProfessional = (TextView) layoutProfessional.findViewById(R.id.tv_right);
        ((TextView)layoutEnterDate.findViewById(R.id.tv_left)).setText("入学年份：");
        tvEnterDate = (TextView) layoutEnterDate.findViewById(R.id.tv_right);
        ((TextView)layoutProject.findViewById(R.id.tv_left)).setText("项目经验：");
        tvProject = (TextView) layoutProject.findViewById(R.id.tv_right);
        ((TextView)layoutWorks.findViewById(R.id.tv_left)).setText("工作/实习经历：");
        tvWorks = (TextView) layoutWorks.findViewById(R.id.tv_right);
        ((TextView)layoutCv.findViewById(R.id.tv_left)).setText("电子版简历：");
        tvCv = (TextView) layoutCv.findViewById(R.id.tv_right);
        ((TextView)layoutEvaluation.findViewById(R.id.tv_left)).setText("自我评价：");
        tvEvaluation = (TextView) layoutEvaluation.findViewById(R.id.tv_right);
        ((TextView)layoutQQ.findViewById(R.id.tv_left)).setText("QQ：");
        tvQQ = (TextView) layoutQQ.findViewById(R.id.tv_right);
        ((TextView)layoutWechat.findViewById(R.id.tv_left)).setText("微信：");
        tvWechat = (TextView) layoutWechat.findViewById(R.id.tv_right);
        ((TextView)layoutPhone.findViewById(R.id.tv_left)).setText("手机号码：");
        tvPhone = (TextView) layoutPhone.findViewById(R.id.tv_right);
        tvPhone.setTextColor(getResources().getColor(R.color.color_666666));
        LinearLayout.LayoutParams phoneParam = (LinearLayout.LayoutParams) tvPhone.getLayoutParams();
        phoneParam.rightMargin = DisplayUtils.dip2px(this, 15);
        tvPhone.setLayoutParams(phoneParam);
        layoutPhone.findViewById(R.id.iv_right).setVisibility(View.GONE);
        ((TextView)layoutEmail.findViewById(R.id.tv_left)).setText("邮箱：");
        tvEmail = (TextView) layoutEmail.findViewById(R.id.tv_right);

        if(galleryConfig == null) {
            IHandlerCallBack iHandlerCallBack = new IHandlerCallBack() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(List<String> photoList) {
                    if (photoList != null && photoList.size() > 0) {
                        File file = FileUtils.compressImageFromPath(ProfileInfoActivity.this, photoList.get(0));
                        RequestBody requestFile = RequestBody.create(MediaType.parse(Constants.FORM_DATA), file);
                        memberAvatar = MultipartBody.Part.createFormData(Constants.FILE_PARAM, file.getName(), requestFile);

                        if(memberAvatar == null){
                            return;
                        }
                        uploadPresenter.upload();

                        Application.application.getImageLoader().displayImage(Constants.FILE_PREFIX + photoList.get(0),
                                ivAvatar, Application.application.getDefaultOptions());
                        ivAvatar.setVisibility(View.VISIBLE);
                        tvAvatar.setVisibility(View.INVISIBLE);
                    } else {
                        ivAvatar.setVisibility(View.GONE);
                        tvAvatar.setVisibility(View.VISIBLE);
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

    private void fillData(MemberBean member){
        tvNickName.setText(member.getNick_name());
        if(!TextUtils.isEmpty(member.getAvatar_rsurl())){
            ivAvatar.setVisibility(View.VISIBLE);
            tvAvatar.setVisibility(View.GONE);
            Application.application.getImageLoader().displayImage(member.getAvatar_rsurl(), ivAvatar);
        } else {
            ivAvatar.setVisibility(View.GONE);
            tvAvatar.setVisibility(View.VISIBLE);
        }
        tvSign.setText(member.getMy_signature());

        tvRealName.setText(member.getName());
        if(member.getGender() != null) {
            tvGender.setText(member.getGender().intValue() == 1 ? "男" : "女");
            gender = member.getGender().intValue();
        } else {
            gender = -1;
            tvGender.setText("");
        }
        tvBirthday.setText(DateUtils.dateToString(member.getBirthday(), DateUtils.DATE_PATTERN));

        tvSchool.setText(member.getEnter_school());
        tvProfessional.setText(member.getProfession());

        tvEvaluation.setText(member.getSelf_evaluation());

        tvQQ.setText(member.getQq());
        tvWechat.setText(member.getWeixin());
        tvPhone.setText(member.getMobile());
        tvEmail.setText(member.getEmail());

        tvSchool.setText(member.getCollege());
        tvProfessional.setText(member.getProfession());
        tvProject.setText(member.getProject_experience());
        tvWorks.setText(member.getWork_experience());
        tvEvaluation.setText(member.getSelf_evaluation());

        if(member.getBirthday() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(member.getBirthday());
            tvBirthday.setText(DateUtils.dateToString(member.getBirthday(), DateUtils.DATE_PATTERN));

            birthYear = calendar.get(Calendar.YEAR);
            birthMonth = calendar.get(Calendar.MONTH) + 1;
            birthDay = calendar.get(Calendar.DAY_OF_MONTH);
        }

        if(!TextUtils.isEmpty(member.getEnter_school())){
            String[] enters = member.getEnter_school().split("-");
            if(enters.length > 0){
                enterSchoolYear = Integer.parseInt(enters[0]);
            }
            if(enters.length > 1){
                enterSchoolMonth = Integer.parseInt(enters[1]);
            }
            if(enters.length > 2){
                enterSchoolDay = Integer.parseInt(enters[2]);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, enterSchoolYear);
            calendar.set(Calendar.MONTH, enterSchoolMonth - 1);
            calendar.set(Calendar.DAY_OF_MONTH, enterSchoolDay);
            tvEnterDate.setText(DateUtils.calendarToString(calendar, DateUtils.DATE_PATTERN));
        }
    }

    @OnClick(R.id.layout_avatar)
    void clickAvatar(){
        if(uploadPresenter == null){
            uploadPresenter = new UploadPresenter(new UploadModel(), this);
        }

        MangoUtils.premissionsRequest(this, new MangoUtils.OnPremissionsGrantedListener() {
            @Override
            public void onAllGranted() {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(ProfileInfoActivity.this);
            }
        });
    }

    @OnClick(R.id.ib_left)
    void clickBack(View v){
        presenter.settingMember();
    }

    @OnClick(R.id.layout_cv)
    void clickCv(View v){
        if(!TextUtils.isEmpty(member.getResume_rsurl())) {
            ActivityBuilder.startSystemBrowser(this, Uri.parse(member.getResume_rsurl()));
        }
    }

    @OnClick(R.id.layout_birthday)
    void clickBirthday(View v){
        MangoDateItemPicker picker = new MangoDateItemPicker(this, birthYear, birthMonth, birthDay);
        DialogUtil.createDatePickerDialog(this, "选择出生日期", picker, "确定", "取消", new DialogUtil.OnChooseDialogListener() {
            @Override
            public void onChoose() {
                birthYear = Integer.parseInt(picker.getYear());
                birthMonth = Integer.parseInt(picker.getMonth());
                birthDay = Integer.parseInt(picker.getDay());
                newBirthday = DateUtils.stringToDate(picker.getDate(), DateUtils.DATE_PATTERN);
                tvBirthday.setText(picker.getDate());
            }
        });
    }

    @OnClick(R.id.layout_enter_date)
    void clickEnterSchool(View v){
        MangoDateItemPicker picker = new MangoDateItemPicker(this, enterSchoolYear, enterSchoolMonth, enterSchoolDay);
        DialogUtil.createDatePickerDialog(this, "选择入学日期", picker, "确定", "取消", new DialogUtil.OnChooseDialogListener() {
            @Override
            public void onChoose() {
                enterSchoolYear = Integer.parseInt(picker.getYear());
                enterSchoolMonth = Integer.parseInt(picker.getMonth());
                enterSchoolDay = Integer.parseInt(picker.getDay());
                newEnterSchool = DateUtils.stringToDate(picker.getDate(), DateUtils.DATE_PATTERN);
                tvEnterDate.setText(picker.getDate());
            }
        });
    }

    @OnClick(R.id.layout_gender)
    void clickGender(View v){
        RadioGroup group = (RadioGroup) getLayoutInflater().inflate(R.layout.layout_select_gender_in_dialog, null, false);
        RadioButton male = (RadioButton) group.findViewById(R.id.rb_male);
        RadioButton female = (RadioButton) group.findViewById(R.id.rb_female);
        if("男".equals(tvGender.getText())){
            male.setChecked(true);
        } else if("女".equals(tvGender.getText())){
            female.setChecked(true);
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(male.getId() == checkedId){
                    selectedGender = 1;
                } else if(female.getId() == checkedId){
                    selectedGender = 0;
                } else {
                    selectedGender = -1;
                }
            }
        });
        DialogUtil.createDatePickerDialog(this, "选择性别", group, "确定", "取消", new DialogUtil.OnChooseDialogListener() {
            @Override
            public void onChoose() {
                if(selectedGender >= 0) {
                    gender = selectedGender;
                    tvGender.setText(gender == 1 ? "男" : "女");
                }
            }
        });
    }

    @OnClick(R.id.layout_qq)
    void clickQQ(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改QQ", "确定", "QQ", 20, tvQQ.getText().toString(), 1);
    }

    @OnClick(R.id.layout_wechat)
    void clickWechat(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改微信", "确定", "wechat", 50, tvWechat.getText().toString());
    }

    @OnClick(R.id.layout_sign)
    void clickSign(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改签名", "确定", "sign", 50, tvSign.getText().toString());
    }

    @OnClick(R.id.layout_email)
    void clickEmail(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改邮箱", "确定", "email", 50, tvEmail.getText().toString());
    }

    @OnClick(R.id.layout_nick_name)
    void clickNickName(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改昵称", "确定", "nick_name", 20, tvNickName.getText().toString());
    }

    @OnClick(R.id.layout_school)
    void clickSchool(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改院校", "确定", "school", 50, tvSchool.getText().toString());
    }

    @OnClick(R.id.layout_professional)
    void clickProfessional(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改专业", "确定", "professional", 50, tvProfessional.getText().toString());
    }

    @OnClick(R.id.layout_project)
    void clickProject(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改项目经验", "确定", "project", 200, tvProject.getText().toString());
    }

    @OnClick(R.id.layout_works)
    void clickWorks(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改实习经历", "确定", "works", 200, tvWorks.getText().toString());
    }

    @OnClick(R.id.layout_evaluation)
    void clickEvaluation(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改自我评价", "确定", "evaluation", 200, tvEvaluation.getText().toString());
    }

    @OnClick(R.id.layout_real_name)
    void clickRealName(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改姓名", "确定", "real_name", 10, tvRealName.getText().toString());
    }

    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event){
        String type = event.getType();
        if("QQ".equals(type)){
            tvQQ.setText(event.getContent());
        } else if("nick_name".equals(type)){
            tvNickName.setText(event.getContent());
        } else if("wechat".equals(type)){
            tvWechat.setText(event.getContent());
        } else if("sign".equals(type)){
            tvSign.setText(event.getContent());
        } else if("email".equals(type)){
            tvEmail.setText(event.getContent());
        } else if("school".equals(type)){
            tvSchool.setText(event.getContent());
        } else if("professional".equals(type)){
            tvProfessional.setText(event.getContent());
        } else if("project".equals(type)){
            tvProject.setText(event.getContent());
        } else if("works".equals(type)){
            tvWorks.setText(event.getContent());
        } else if("evaluation".equals(type)){
            tvEvaluation.setText(event.getContent());
        } else if("real_name".equals(event.getType())){
            tvRealName.setText(event.getContent());
        }
    }

    @Override
    public void onBackPressed() {
        presenter.settingMember();
    }

    @Override
    protected void onDestroy() {
        Bus.getDefault().unregister(this);
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
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
        member.setQq(tvQQ.getText().toString());
        member.setNick_name(tvNickName.getText().toString());
        if(uploadBean != null){
            member.setAvatar_rsurl(uploadBean.getUrl());
        }
        member.setMy_signature(tvSign.getText().toString());
        member.setWeixin(tvWechat.getText().toString());
        member.setEmail(tvEmail.getText().toString());
        member.setCollege(tvSchool.getText().toString());
        member.setProfession(tvProfessional.getText().toString());
        member.setProject_experience(tvProject.getText().toString());
        member.setWork_experience(tvWorks.getText().toString());
        member.setSelf_evaluation(tvEvaluation.getText().toString());
        if(newBirthday != null){
            member.setBirthday(newBirthday);
        }
        if(newEnterSchool != null){
            member.setEnter_school(tvEnterDate.getText().toString());
        }
        if(gender >= 0){
            member.setGender(gender);
        }
        member.setName(tvRealName.getText().toString());
        Application.application.saveMember(member, Application.application.getSessId());

        BusEvent.RefreshMemberEvent event = new BusEvent.RefreshMemberEvent();
        Bus.getDefault().postSticky(event);

        finish();
    }

    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("qq", tvQQ.getText().toString());
        map.put("nick_name", tvNickName.getText().toString());
        map.put("weixin", tvWechat.getText().toString());
        map.put("my_signature", tvSign.getText().toString());
        map.put("email", tvEmail.getText().toString());
        if(uploadBean != null){
            map.put("avatar_rsurl", uploadBean.getFile_name());
        }
        map.put("college", tvSchool.getText().toString());
        map.put("profession", tvProfessional.getText().toString());
        map.put("project_experience", tvProject.getText().toString());
        map.put("work_experience", tvWorks.getText().toString());
        map.put("self_evaluation", tvEvaluation.getText().toString());
        if(newBirthday != null) {
            map.put("birthday", tvBirthday.getText().toString());
        }
        if(newEnterSchool != null) {
            map.put("enter_school", tvEnterDate.getText().toString());
        }
        if(gender >= 0){
            map.put("gender", gender);
        }
        map.put("name", tvRealName.getText().toString());
        return map;
    }

    @Override
    public void onSuccess(UploadBean upload) {
        this.uploadBean = upload;
    }

    @Override
    public void onUploadFailure(String message) {
        AppUtils.showToast(this, message);
        this.uploadBean = null;
    }

    @Override
    public void beforeUpload() {
        this.uploadBean = null;
        AppUtils.showToast(this, R.string.picture_uploading);
        layoutAvatar.setEnabled(false);
    }

    @Override
    public void afterUpload(boolean success) {
        if(success){
            AppUtils.showToast(this, R.string.picture_upload_success);
        } else {
            AppUtils.showToast(this, R.string.picture_upload_failure);
        }
        layoutAvatar.setEnabled(true);
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
    public MultipartBody.Part getFile() {
        return memberAvatar;
    }
}
