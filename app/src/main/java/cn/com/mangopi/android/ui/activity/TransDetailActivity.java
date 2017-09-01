package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.util.DateUtils;

public class TransDetailActivity extends BaseTitleBarActivity {

    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_type_tip)
    TextView tvTypeTip;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.tv_type_label)
    TextView tvTypeLabel;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_balance)
    TextView tvBanlance;

    TransListBean trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trans = (TransListBean) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);
        if(trans == null){
            finish();
        }
        setContentView(R.layout.activity_trans_detail);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.member_trans_detail);

        tvType.setText(trans.getType());
        tvTypeTip.setText(trans.getType()+"：");
        if(trans.getAmount() != null) {
            tvAmount.setText(trans.getAmount().toString());
        } else {
            tvAmount.setText("");
        }
        tvTypeLabel.setText(trans.getType_label());
        tvTime.setText(DateUtils.dateToString(trans.getCreate_time(), DateUtils.DATE_MONTH_DAY_CN));
        if(trans.getBalance() != null){
            tvBanlance.setText(trans.getBalance().toString());
        } else {
            tvBanlance.setText("");
        }
    }
}
