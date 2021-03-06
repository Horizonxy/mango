package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.UpdateRolePresenter;
import cn.com.mangopi.android.ui.viewlistener.UpdateRoleListener;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class UpgradeRoleActivity extends BaseTitleBarActivity implements UpdateRoleListener {

    @Bind(R.id.tv_current_role)
    TextView tvCurrentRole;
    @Bind(R.id.layout_student)
    View vStudent;
    @Bind(R.id.layout_teacher)
    View vTeacher;
    @Bind(R.id.layout_company)
    View vCompany;
    @Bind(R.id.layout_community)
    View vCommunity;
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

        ((ImageView)vStudent.findViewById(R.id.iv_role)).setImageResource(R.drawable.selector_img_role_student_tag);
        ((ImageView)vTeacher.findViewById(R.id.iv_role)).setImageResource(R.drawable.selector_img_role_tutor_tag);
        ((ImageView)vCompany.findViewById(R.id.iv_role)).setImageResource(R.drawable.selector_img_role_company_tag);
        ((ImageView) vCommunity.findViewById(R.id.iv_role)).setImageResource(R.drawable.selector_img_role_commutiny_tag);
        ((TextView)vStudent.findViewById(R.id.tv_title)).setText("验证学生身份");
        ((TextView)vTeacher.findViewById(R.id.tv_title)).setText("升级为导师");
        ((TextView)vCompany.findViewById(R.id.tv_title)).setText("注册为企业");
        ((TextView) vCommunity.findViewById(R.id.tv_title)).setText("注册为社团");
        ((TextView)vStudent.findViewById(R.id.tv_desc)).setText("验证您的学生身份，可以参加芒果树的各种校园活动，包括知名企业市场推广兼职活动等。您只需提供学校、专业和学位证照片即可通过验证。");
        ((TextView)vTeacher.findViewById(R.id.tv_desc)).setText("只要您有在知名企业任职、或者拥有一技之长，并且乐于助人，就来加入我们的导师行列吧。在这里您将发现自身的价值、打造个人的品牌，并增值您的财富。");
        ((TextView)vCompany.findViewById(R.id.tv_desc)).setText("注册为企业后，您可以发布企业的市场推广项目，比如一场校园营销或者一个品牌的设计，从而实现与芒果树的成员进行互动。");
        ((TextView) vCommunity.findViewById(R.id.tv_desc)).setText("芒果树社团组织为校内外的社团组织，可以是全国范围的兴趣爱好聚集地，也可以是同城的社群，注册社团后，您可以通过芒果树发布动态、组织活动。");
    }

    @OnClick(R.id.layout_student)
    void studentClick(View v){
        presenter.checkUpgradeStudent();
    }

    @OnClick(R.id.layout_teacher)
    void teacherClick(View v){
        presenter.checkUpgradeTutor();
    }

    @OnClick(R.id.layout_community)
    void corporationClick(View v){
        presenter.checkUpgradeCommunity();
    }

    @OnClick(R.id.layout_company)
    void companyClick(View v){
        presenter.checkUpgradeCompany();
    }

    @Override
    public void onSuccess(Constants.UserIndentity indentity) {
        if(indentity == Constants.UserIndentity.STUDENT){
            startActivity(new Intent(this, UpgradeToStudentActivity.class));
        } else if(indentity == Constants.UserIndentity.TUTOR){
            startActivity(new Intent(this, UpgradeToTutorActivity.class));
        } else if(indentity == Constants.UserIndentity.COMMUNITY){
            startActivity(new Intent(this, UpgradeToCommunityActivity.class));
        } else if(indentity == Constants.UserIndentity.COMPANY){
            startActivity(new Intent(this, UpgradeToCompanyActivity.class));
        }
    }

    @Override
    public Map<String, Object> getUpgradeMap() {
        return null;
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
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
