package com.mango.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mango.R;
import com.mango.ui.widget.TitleBar;
import com.mango.util.DialogUtil;

public class TestActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener, View.OnClickListener{

    Button btnImportant;
    Button btnLesser;
    Button btnRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
    }

    private void initView() {
        titleBar.setLeftBtnIcon(R.mipmap.ic_launcher);
        titleBar.setTvLeftBack(true);
        titleBar.setLeftText("返回");
        titleBar.setTitle("芒果派");
        titleBar.setOnTitleBarClickListener(this);
        titleBar.setRightBtnIcon(R.mipmap.ic_launcher);
        titleBar.setSecondRightBtnIcon(R.mipmap.ic_launcher);

        btnImportant = (Button) findViewById(R.id.btn_important);
        btnLesser = (Button) findViewById(R.id.btn_lesser);
        btnRect = (Button) findViewById(R.id.btn_rect);

        btnImportant.setOnClickListener(this);
        btnLesser.setOnClickListener(this);
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.ib_right:
                Toast.makeText(this, "right icon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_second_right:
                btnImportant.setEnabled(false);
                btnLesser.setEnabled(false);
                btnRect.setEnabled(false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_important:
                startActivity(new Intent(this, MyOrderListActivity.class));
                break;
            case R.id.btn_lesser:
//                startActivity(new Intent(this, TextViewPagerActivity.class));
//                DialogUtil.createAlertDialog(this, "银行卡删除成功", "知道了");
                DialogUtil.createChoosseDialog(this, "是否删除", "软件课程.doc？", "确定", "取消", null);
                break;
        }
    }
}
