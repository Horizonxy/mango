package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.MemberBean;
import com.mango.model.data.MemberModel;
import com.mango.presenter.UpdateRolePresenter;
import com.mango.ui.viewlistener.UpdateRoleListener;
import com.mango.util.AppUtils;
import com.mango.util.DialogUtil;
import com.mango.util.MangoUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class UpdateRoleActivity extends BaseTitleBarActivity implements UpdateRoleListener {

    @Bind(R.id.tv_current_role)
    TextView tvCurrentRole;
    @Bind(R.id.layout_student)
    View vStudent;
    @Bind(R.id.layout_teacher)
    View vTeacher;
    @Bind(R.id.layout_company)
    View vCompany;
    @Bind(R.id.layout_corporation)
    View vCorporation;
    MemberBean member;
    List<Constants.UserIndentity> indentityList;

    UpdateRolePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_role);

        initView();
        indentityList = MangoUtils.getIndentityList();
        presenter = new UpdateRolePresenter(new MemberModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.update_my_role);

        tvCurrentRole.setText(String.format(getString(R.string.my_current_role),
                Application.application.getMember().getUser_identity_label()));

        ((ImageView)vStudent.findViewById(R.id.iv_role)).setImageResource(R.drawable.icon_s);
        ((ImageView)vTeacher.findViewById(R.id.iv_role)).setImageResource(R.drawable.icon_t);
        ((ImageView)vCompany.findViewById(R.id.iv_role)).setImageResource(R.drawable.icon_e);
        ((ImageView)vCorporation.findViewById(R.id.iv_role)).setImageResource(R.drawable.icon_a);
        ((TextView)vStudent.findViewById(R.id.tv_title)).setText("验证学生省份");
        ((TextView)vTeacher.findViewById(R.id.tv_title)).setText("升级为导师");
        ((TextView)vCompany.findViewById(R.id.tv_title)).setText("注册为企业");
        ((TextView)vCorporation.findViewById(R.id.tv_title)).setText("注册为社团");
        ((TextView)vStudent.findViewById(R.id.tv_desc)).setText("验证您的学生身份，可以参加芒果树的各种校园活动，包括知名企业市场推广兼职活动等。您只需提供学校、专业和学位证照片即可通过验证。");
        ((TextView)vTeacher.findViewById(R.id.tv_desc)).setText("只要您有在知名企业任职、或者拥有一技之长，并且乐于助人，就来加入我们的导师行列吧。在这里您将发现自身的价值、打造个人的品牌，并增值您的财富。");
        ((TextView)vCompany.findViewById(R.id.tv_desc)).setText("注册为企业后，您可以发布企业的市场推广项目，比如一场校园营销或者一个品牌的设计，从而实现与芒果树的成员进行互动。");
        ((TextView)vCorporation.findViewById(R.id.tv_desc)).setText("芒果树社团组织为校内外的社团组织，可以是全国范围的兴趣爱好聚集地，也可以是同城的社群，注册社团后，您可以通过芒果树发布动态、组织活动。");
    }

    @OnClick(R.id.layout_student)
    void studentClick(View v){
        if(indentityList.contains(Constants.UserIndentity.COMPANY)){
            DialogUtil.createUpdateRoleAlertDialog(this, getString(R.string.check_student_role),
                    "您当前的身份是"+member.getUser_identity_label()+"\n不可再拥有学生身份", "好的");
            return;
        } else {
            presenter.checkUpgradeStudent();
        }
    }

    @OnClick(R.id.layout_teacher)
    void teacherClick(View v){
        presenter.checkUpgradeTutor();
    }

    @OnClick(R.id.layout_corporation)
    void corporationClick(View v){
        if(indentityList.contains(Constants.UserIndentity.COMPANY)){
            DialogUtil.createUpdateRoleAlertDialog(this, getString(R.string.check_community_role),
                    "您当前的身份是"+member.getUser_identity_label()+"\n不可再注册为社团", "好的");
            return;
        } else {
            presenter.checkUpgradeCommunity();
        }
    }

    @OnClick(R.id.layout_company)
    void companyClick(View v){
        if(indentityList.contains(Constants.UserIndentity.STUDENT) || indentityList.contains(Constants.UserIndentity.COMMUNITY)){
            DialogUtil.createUpdateRoleAlertDialog(this, getString(R.string.check_company_role),
                    "您当前的身份是"+member.getUser_identity_label()+"\n不可再注册为企业", "好的");
            return;
        } else {
            presenter.checkUpgradeCompany();
        }
    }

    @Override
    public void onSuccess(Constants.UserIndentity indentity) {

    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }
}
