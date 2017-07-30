package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;

public class SelectPayActivity extends BaseTitleBarActivity {

    OrderBean order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);

        order = (OrderBean) getIntent().getSerializableExtra(Constants.BUNDLE_ORDER);
        if(order == null){
            finish();
        }
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.pay_for_order);

    }
}
