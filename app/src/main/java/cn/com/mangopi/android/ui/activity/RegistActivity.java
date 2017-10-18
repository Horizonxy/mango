package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.LoginPresenter;
import cn.com.mangopi.android.ui.viewlistener.LoginListener;
import cn.com.mangopi.android.ui.widget.TitleBar;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

public class RegistActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener,LoginListener<RegistBean> {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_verify_code)
    EditText etVerifyCode;
    @Bind(R.id.btn_regist)
    Button btnRegist;
    @Bind(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;

    CountDownTimer getCodeCountDownTimer;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
        loginPresenter = new LoginPresenter(new MemberModel(), this);
    }

    private void initView() {
        titleBar.setTitle("初次注册");

        setGetVerifyCodeTvWidth();
    }

    private void setGetVerifyCodeTvWidth() {
        tvGetVerifyCode.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int width = tvGetVerifyCode.getMeasuredWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvGetVerifyCode.getLayoutParams();
                params.width = width;
                tvGetVerifyCode.setLayoutParams(params);
                tvGetVerifyCode.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    @OnClick(R.id.tv_get_verify_code)
    void getVerifyCode(View v){
        loginPresenter.getVerifyCode(etPhone.getText().toString());
    }

    @OnClick(R.id.btn_regist)
    void userRegist(View v){
        loginPresenter.quickLogin(etPhone.getText().toString(), etVerifyCode.getText().toString());
    }

    @Override
    public void onTitleButtonClick(View view) {
        if(view.getId() == R.id.ib_right){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if(loginPresenter != null){
            loginPresenter.onDestroy();
        }
        if(getCodeCountDownTimer != null){
            getCodeCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onSuccess(RegistBean data) {
        if(data == null) {
            tvGetVerifyCode.setEnabled(false);
            getCodeCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvGetVerifyCode.setText(millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    tvGetVerifyCode.setEnabled(true);
                    tvGetVerifyCode.setText(getString(R.string.get_verify_code));
                }
            };
            getCodeCountDownTimer.start();
        }
    }

    @Override
    public void startSetNickName() {
        ActivityBuilder.startSetNickNameActivity(this);
        ActivityBuilder.defaultTransition(this);
    }

    @Override
    public void startMain() {
        ActivityBuilder.startMainActivity(this);
        ActivityBuilder.defaultTransition(this);
        finish();
    }

    @Override
    public void startRegist() {
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this,message);
    }

    @Override
    public Context currentContext() {
        return this;
    }
}
