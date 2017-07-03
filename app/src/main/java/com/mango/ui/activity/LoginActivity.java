package com.mango.ui.activity;

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

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.mango.R;
import com.mango.ui.widget.TitleBar;
import com.mango.util.BusEvent;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

public class LoginActivity extends BaseTitleBarActivity {

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

    CountDownTimer getCodeCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bus.getDefault().register(this);

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
        initProtocol();
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
    }
}
