package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.mcxiaoke.bus.Bus;
import java.util.HashMap;
import java.util.Map;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CompanyDetailBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.UpdateRolePresenter;
import cn.com.mangopi.android.ui.viewlistener.CompanyListener;
import cn.com.mangopi.android.ui.viewlistener.UpdateRoleListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import rx.functions.Action1;

public class UpgradeToCompanyActivity extends BaseTitleBarActivity implements UpdateRoleListener, CompanyListener {

    @Bind(R.id.et_company_no)
    EditText etCompaynNo;
    @Bind(R.id.tv_company_name)
    TextView tvCompanyName;
    @Bind(R.id.et_my_name)
    EditText etMyName;
    @Bind(R.id.et_my_jobs)
    EditText etMyJobs;

    MemberBean member;
    UpdateRolePresenter updateRolePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_to_company);
        Bus.getDefault().register(this);

        member = Application.application.getMember();
        if(member == null){
            finish();
        }

        updateRolePresenter = new UpdateRolePresenter(new MemberModel(), this);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.check_company_role);

        RxTextView.afterTextChangeEvents(etCompaynNo).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
            @Override
            public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                tvCompanyName.setText("");
            }
        });
    }

    @OnClick(R.id.btn_verify_company_no)
    void verifyCompanyNo(View v){
        if(TextUtils.isEmpty(etCompaynNo.getText())){
            AppUtils.showToast(this, "请输入企业编号");
            return;
        }
        updateRolePresenter.getCompany();
    }

    @OnClick(R.id.btn_submit)
    void upgradeClick(View v){
        if(TextUtils.isEmpty(etCompaynNo.getText())){
            AppUtils.showToast(this, "请输入企业编号");
            return;
        }
        if(TextUtils.isEmpty(tvCompanyName.getText())){
            AppUtils.showToast(this, "请验证企业编号获取企业名称");
            return;
        }
        if(TextUtils.isEmpty(etMyName.getText())){
            AppUtils.showToast(this, "请输入姓名");
            return;
        }
        if(TextUtils.isEmpty(etMyJobs.getText())){
            AppUtils.showToast(this, "请输入职业(身份)");
            return;
        }
        updateRolePresenter.upgradeCompany();
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
        ActivityBuilder.startSuccessActivity(this, getString(R.string.check_company_role), "信息已提交成功，请等候系统确认。");
        finish();
    }

    @Override
    public Map<String, Object> getUpgradeMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("company_no", etCompaynNo.getText().toString());
        map.put("company_name", tvCompanyName.getText().toString());
        map.put("name", etMyName.getText().toString());
        map.put("job", etMyJobs.getText().toString());
        map.put("lst_sessid", Application.application.getSessId());
        return map;
    }

    @Override
    protected void onDestroy() {
        Bus.getDefault().unregister(this);
        super.onDestroy();

        if(updateRolePresenter != null){
            updateRolePresenter.onDestroy();
        }
    }

    @Override
    public void onCompanySuccess(CompanyDetailBean companyDetail) {
        tvCompanyName.setText(companyDetail.getCompany_name());
    }

    @Override
    public String getCompanyNo() {
        return etCompaynNo.getText().toString();
    }
}
