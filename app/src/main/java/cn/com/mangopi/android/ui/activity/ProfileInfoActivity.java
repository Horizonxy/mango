package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.SettingMemberPresenter;
import cn.com.mangopi.android.ui.viewlistener.ProfileSettingListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DateUtils;

public class ProfileInfoActivity extends BaseTitleBarActivity implements ProfileSettingListener {

    MemberBean member;
    @Bind(R.id.layout_avatar)
    View layoutAvatar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        Bus.getDefault().register(this);

        member = Application.application.getMember();
        if(member == null){
            finish();
        }

        initView();
        fillData(member);
        presenter = new SettingMemberPresenter(new MemberModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.profile_info);

        ((TextView)layoutAvatar.findViewById(R.id.tv_left)).setText("头像：");
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
        ((TextView)layoutEmail.findViewById(R.id.tv_left)).setText("邮箱：");
        tvEmail = (TextView) layoutEmail.findViewById(R.id.tv_right);
    }

    private void fillData(MemberBean member){
        tvNickName.setText(member.getNick_name());

        tvRealName.setText(member.getName());
        if(member.getGender() != null) {
            tvGender.setText(member.getGender() == 1 ? "男" : "女");
        } else {
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
    }

    @OnClick(R.id.ib_left)
    void clickBack(View v){
        presenter.settingMember();
    }

    @OnClick(R.id.layout_qq)
    void clickQQ(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改QQ", "确定", "QQ", 20, tvQQ.getText().toString());
    }

    @OnClick(R.id.layout_nick_name)
    void clickNickName(View v){
        ActivityBuilder.startInputMessageActivity(this, "修改昵称", "确定", "nick_name", 20, tvNickName.getText().toString());
    }

    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event){
        String type = event.getType();
        if("QQ".equals(type)){
            tvQQ.setText(event.getContent());
        } else if("nick_name".equals(type)){
            tvNickName.setText(event.getContent());
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
        return map;
    }
}
