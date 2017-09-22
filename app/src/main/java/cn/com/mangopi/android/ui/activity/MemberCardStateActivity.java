package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;

public class MemberCardStateActivity extends BaseTitleBarActivity {

    @Bind(R.id.iv_state)
    ImageView ivState;
    @Bind(R.id.tv_state)
    TextView tvState;
    int state;
    String stateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_card_state);

        state = getIntent().getIntExtra(Constants.BUNDLE_TYPE, 0);
        stateLabel = getIntent().getStringExtra(Constants.BUNDLE_CONTENT);

        initView();
    }

    private void initView() {
        titleBar.setTitle("银行卡状态");
        if(-25 == state){
            ivState.setImageResource(R.drawable.icon_wait);
        } else if(50 == state){
            ivState.setImageResource(R.drawable.icon_success);
        } else {
            ivState.setImageResource(R.drawable.icon_fail);
        }
        tvState.setText(stateLabel);
    }

    @OnClick(R.id.btn_back)
    void back(View v){
        finish();
    }
}
