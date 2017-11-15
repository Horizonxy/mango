package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mcxiaoke.bus.Bus;

import butterknife.Bind;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.Inputvalidator;

public class JoinTeamIntroActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_qq)
    EditText etQQ;
    @Bind(R.id.et_intro)
    EditText etIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team_intro);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.self_intro);
        titleBar.setRightText(R.string.save);
        titleBar.setOnTitleBarClickListener(this);
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                if(TextUtils.isEmpty(etPhone.getText())){
                    AppUtils.showToast(this, "请输入手机号");
                    return;
                }
                if(!Inputvalidator.checkPhone(etPhone.getText().toString())){
                    AppUtils.showToast(this, "请输入正确的手机号");
                    return;
                }
                if(TextUtils.isEmpty(etQQ.getText())){
                    AppUtils.showToast(this, "请输入QQ号");
                    return;
                }
                if(TextUtils.isEmpty(etIntro.getText())){
                    AppUtils.showToast(this, "请输入自我介绍");
                    return;
                }
                BusEvent.JoinTeamIntroEvent event = new BusEvent.JoinTeamIntroEvent();
                event.setPhone(etPhone.getText().toString());
                event.setQq(etQQ.getText().toString());
                event.setIntro(etIntro.getText().toString());
                Bus.getDefault().post(event);
                finish();
                break;
        }
    }
}
