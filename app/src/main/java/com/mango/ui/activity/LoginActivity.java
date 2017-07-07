package com.mango.ui.activity;

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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mango.R;
import com.mango.di.component.DaggerLoginActivityComponent;
import com.mango.di.module.LoginActivityModule;
import com.mango.model.bean.RegistBean;
import com.mango.presenter.LoginPresenter;
import com.mango.ui.viewlistener.LoginListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;
import com.mango.util.BusEvent;
import com.mango.wxapi.WXEntryActivity;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

public class LoginActivity extends BaseTitleBarActivity implements LoginListener<RegistBean> {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_verify_code)
    EditText etVerifyCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_protocol)
    TextView tvProtocal;
    @Bind(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @Inject
    LoginPresenter loginPresenter;
    @Bind(R.id.ib_wx_login)
    ImageButton ibWxLogin;
    CountDownTimer getCodeCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bus.getDefault().register(this);
        DaggerLoginActivityComponent.builder().loginActivityModule(new LoginActivityModule(this)).build().inject(this);

        initView();
    }

    private void initView() {
        titleBar.setTitle(getString(R.string.login_mango));
        titleBar.setLeftBtnIconVisibility(View.GONE);

        RxView.clicks(btnLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(LoginActivity.this, SetNickNameActivity.class));
            }
        });
        btnLogin.setEnabled(false);

        RxTextView.textChanges(etPhone).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                btnLogin.setEnabled(etVerifyCode.getText().length() > 0 && etPhone.getText().length() == 11);
            }
        });
        RxTextView.textChanges(etVerifyCode).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                btnLogin.setEnabled(etVerifyCode.getText().length() > 0 && etPhone.getText().length() == 11);
            }
        });

        RxView.clicks(ibWxLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                loginWx();
            }
        });

        RxView.clicks(btnLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                loginPresenter.quickLogin(etPhone.getText().toString(), etVerifyCode.getText().toString());
            }
        });

        initProtocol();
        setGetVerifyCodeTvWidth();
    }

    private void loginWx() {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(this, WXEntryActivity.WEIXIN_APP_ID, true);
        wxApi.registerApp(WXEntryActivity.WEIXIN_APP_ID);
        if (wxApi != null && wxApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            wxApi.sendReq(req);
        } else {
            AppUtils.showToast(this, R.string.uninstalled_wx);
        }
    }

    private void setGetVerifyCodeTvWidth() {
        tvGetVerifyCode.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int width = tvGetVerifyCode.getMeasuredWidth();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvGetVerifyCode.getLayoutParams();
                params.width = width;
                tvGetVerifyCode.setLayoutParams(params);
                return true;
            }
        });
    }

    private void initProtocol() {
        String tip = getResources().getString(R.string.login_protocal_tip);
        String userProtocol = getResources().getString(R.string.user_protocal);

        SpannableString span = new SpannableString(tip + userProtocol);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(LoginActivity.this, userProtocol, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.color_14b2ec));
            }
        }, tip.length(), tip.length() + userProtocol.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvProtocal.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvProtocal.setText(span);
        tvProtocal.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.tv_get_verify_code)
    void getVerifyCode(View v){
        loginPresenter.getVerifyCode(etPhone.getText().toString());
    }

    @BusReceiver
    public void onActivityFinishEvent(BusEvent.ActivityFinishEvent event){
        if(event != null && event.isFinish()){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getCodeCountDownTimer != null){
            getCodeCountDownTimer.cancel();
            getCodeCountDownTimer = null;
        }
        Bus.getDefault().unregister(this);

        loginPresenter.onDestroy();
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
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }
}
