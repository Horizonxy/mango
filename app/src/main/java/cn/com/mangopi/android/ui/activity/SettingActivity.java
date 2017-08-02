package cn.com.mangopi.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.SetTransPwdDialog;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseTitleBarActivity {

    @Bind(R.id.layout_profile_info)
    View vProfileInfo;
    @Bind(R.id.layout_phone_num)
    View vPhoneNum;
    @Bind(R.id.layout_trans_pwd)
    View vTransPwd;
    @Bind(R.id.layout_about_us)
    View vAboutUs;
    @Bind(R.id.layout_share_to_friend)
    View vShareToFriend;
    @Bind(R.id.layout_clear_cache)
    View vClearCache;

    TextView tvProfileLeft, tvProfileRight;
    TextView tvPhoneNunLeft, tvPhoneNunRight;
    TextView tvTransPwdLeft, tvTransPwdRight;
    TextView tvAboutUsLeft, tvAboutUsRight;
    TextView tvShareToLeft, tvShareToRight;
    TextView tvClearCacheft, tvClearCacheRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        titleBar.setTitle(getString(R.string.setting));

        tvProfileLeft = (TextView) vProfileInfo.findViewById(R.id.tv_left);
        tvProfileRight = (TextView) vProfileInfo.findViewById(R.id.tv_right);
        tvPhoneNunLeft = (TextView) vPhoneNum.findViewById(R.id.tv_left);
        tvPhoneNunRight = (TextView) vPhoneNum.findViewById(R.id.tv_right);
        vPhoneNum.findViewById(R.id.iv_right).setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvPhoneNunRight.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.rightMargin = (int) getResources().getDimension(R.dimen.dp_15);
        tvPhoneNunRight.setLayoutParams(params);
        tvTransPwdLeft = (TextView) vTransPwd.findViewById(R.id.tv_left);
        tvTransPwdRight = (TextView) vTransPwd.findViewById(R.id.tv_right);
        tvAboutUsLeft = (TextView) vAboutUs.findViewById(R.id.tv_left);
        tvAboutUsRight = (TextView) vAboutUs.findViewById(R.id.tv_right);
        tvShareToLeft = (TextView) vShareToFriend.findViewById(R.id.tv_left);
        tvShareToRight = (TextView) vShareToFriend.findViewById(R.id.tv_right);
        tvClearCacheft = (TextView) vClearCache.findViewById(R.id.tv_left);
        tvClearCacheRight = (TextView) vClearCache.findViewById(R.id.tv_right);

        tvProfileLeft.setText(getString(R.string.profile_info));
        tvProfileRight.setText(getString(R.string.click_to_edit));
        tvPhoneNunLeft.setText(getString(R.string.phone_num));
        tvPhoneNunRight.setText("18583681149");
        tvPhoneNunRight.setTextColor(getResources().getColor(R.color.color_666666));
        tvTransPwdLeft.setText(getString(R.string.trans_pwd));
        tvTransPwdRight.setText(getString(R.string.un_setting));
        tvAboutUsLeft.setText(getString(R.string.about_us));
        tvShareToLeft.setText(getString(R.string.share_to_friend));
        tvClearCacheft.setText(getString(R.string.clear_cache));

    }

    @OnClick(R.id.layout_profile_info)
    void profileClick(View v){
        ActivityBuilder.startProfileInfoActivity(this);
    }

    @OnClick(R.id.layout_trans_pwd)
    void setTransPwd(View v){
        new SetTransPwdDialog(this, new SetTransPwdDialog.onPwdFinishListener() {
            @Override
            public void onFinish(String pwd) {
                AppUtils.showToast(SettingActivity.this, pwd);
            }
        }).show();
    }
}
