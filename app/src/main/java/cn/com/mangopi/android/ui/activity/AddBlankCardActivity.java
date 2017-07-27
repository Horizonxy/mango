package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.viewlistener.AddBlankCardListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

import butterknife.OnClick;

public class AddBlankCardActivity extends BaseTitleBarActivity implements AddBlankCardListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blank_card);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.add_blank_card);
    }

    @OnClick(R.id.btn_add)
    void addCardClick(View v){

    }

    @Override
    public void onSuccess() {
        ActivityBuilder.startCardListActivity(this, null);
    }

    @Override
    public String getBlankName() {
        return null;
    }

    @Override
    public String getCardNo() {
        return null;
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
