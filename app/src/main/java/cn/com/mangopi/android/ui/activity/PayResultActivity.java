package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ShowPayResultBean;
import cn.com.mangopi.android.util.DateUtils;

public class PayResultActivity extends BaseTitleBarActivity {

    ShowPayResultBean result;
    @Bind(R.id.iv_result)
    ImageView ivResult;
    @Bind(R.id.tv_result_label)
    TextView tvResultLabel;
    @Bind(R.id.tv_pay_price)
    TextView tvPayPrice;
    @Bind(R.id.tv_order_name)
    TextView tvOrderName;
    @Bind(R.id.tv_order_no)
    TextView tvOrderNo;
    @Bind(R.id.tv_pay_date)
    TextView tvPayDate;
    @Bind(R.id.tv_channel)
    TextView tvChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);

        result = (ShowPayResultBean) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.pay_detail);
        if(result.isSuccess()){
            ivResult.setImageResource(R.drawable.icon_success);
            tvResultLabel.setText("支付成功！");
        } else {
            ivResult.setImageResource(R.drawable.icon_fail);
            tvResultLabel.setText("支付失败！");
        }
        tvOrderName.setText(result.getOrderName());
        tvOrderNo.setText(result.getOrderNo());
        tvPayDate.setText(DateUtils.dateToString(result.getPayDate(), DateUtils.DATE_MONTH_DAY_CN));
        String channel = result.getPayChannel();
        String channelLabel = "";
        if(Constants.UNION_PAY.equals(channel)){
            channelLabel = getString(R.string.pay_with_card);
        } else if(Constants.ALI_PAY.equals(channel)){
            channelLabel = getString(R.string.pay_with_alipay);
        } else if(Constants.WECHAT_PAY.equals(channel)){
            channelLabel = getString(R.string.pay_with_wx);
        }
        tvChannel.setText(channelLabel);
        tvPayPrice.setText(result.getPayPrice());
    }
}
