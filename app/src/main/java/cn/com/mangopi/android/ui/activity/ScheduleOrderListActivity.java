package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.fragment.MyOrderListFragment;

public class ScheduleOrderListActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_order_list);

        initView();
    }

    private void initView() {
        String sctDate = getIntent().getStringExtra(Constants.BUNDLE_ORDER_SCT_DATE);
        int sctTime = getIntent().getIntExtra(Constants.BUNDLE_ORDER_SCT_TIME, 0);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MyOrderListFragment orderListFragment = MyOrderListFragment.newInstance(2, sctDate, sctTime);
        ft.add(R.id.content, orderListFragment);
        ft.commit();
    }
}
