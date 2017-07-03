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

import com.mango.R;
import com.mango.ui.widget.TitleBar;

import butterknife.Bind;
import butterknife.OnClick;

public class RegistActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.btn_regist)
    Button btnRegist;
    @Bind(R.id.tv_protocol)
    TextView tvProtocal;
    @Bind(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;

    CountDownTimer getCodeCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
    }

    private void initView() {
        titleBar.setLeftBtnIconVisibility(View.GONE);
        titleBar.setRightBtnIcon(R.mipmap.ic_launcher);
        titleBar.setOnTitleBarClickListener(this);

        setGetVerifyCodeTvWidth();
        initProtocol();
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

    private void initProtocol() {
        String tip = getResources().getString(R.string.regist_protocal_tip);
        String userProtocol = getResources().getString(R.string.user_protocal);

        SpannableString span = new SpannableString(tip + userProtocol);
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(RegistActivity.this, userProtocol, Toast.LENGTH_SHORT).show();
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
            }
        };
        getCodeCountDownTimer.start();
    }

    @OnClick(R.id.btn_regist)
    void userRegist(View v){
        startActivity(new Intent(this, SetNickNameActivity.class));
    }

    @Override
    public void onTitleButtonClick(View view) {
        if(view.getId() == R.id.ib_right){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getCodeCountDownTimer != null){
            getCodeCountDownTimer.cancel();
        }
    }
}
