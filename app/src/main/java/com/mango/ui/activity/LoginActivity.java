package com.mango.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mango.R;
import com.mango.ui.widget.TitleBar;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_protocol)
    TextView tvProtocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        titleBar.setLeftBtnIconVisibility(View.GONE);
        titleBar.setRightBtnIcon(R.mipmap.ic_launcher);
        titleBar.setOnTitleBarClickListener(this);

        initProtocol();
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


    @OnClick(R.id.btn_login)
    void userLogin(View v){
        Toast.makeText(LoginActivity.this, "登录", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_regist)
    void gotoRegist(View v){
        startActivity(new Intent(this, RegistActivity.class));
    }

    @Override
    public void onTitleButtonClick(View view) {
        if(view.getId() == R.id.ib_right){
            finish();
        }
    }
}
