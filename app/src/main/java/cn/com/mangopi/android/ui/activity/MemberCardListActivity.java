package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;

import cn.com.mangopi.android.R;

public class MemberCardListActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_card_list);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.my_blank_card);
    }
}
