package com.mango.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mango.R;

public class SetNickNameActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.set_nickname_title);
    }
}
