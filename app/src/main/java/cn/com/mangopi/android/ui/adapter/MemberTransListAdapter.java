package cn.com.mangopi.android.ui.adapter;

import android.content.Context;
import java.util.List;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.DateUtils;

public class MemberTransListAdapter extends QuickAdapter<TransListBean> {
    public MemberTransListAdapter(Context context, int layoutResId, List<TransListBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, TransListBean item) {
        helper.setText(R.id.tv_type_label, item.getType_label())
                .setText(R.id.tv_time, DateUtils.dateToString(item.getCreate_time(), DateUtils.DATE_PATTERN))
                .setVisible(R.id.line_divider, helper.getPosition() < (data.size() - 1));
        if(item.getBalance() != null){
            helper.setText(R.id.tv_balance, "余额：" + item.getBalance().toString());
        } else {
            helper.setText(R.id.tv_balance, "");
        }
        if(item.getAmount() != null){
            helper.setText(R.id.tv_amount, context.getResources().getString(R.string.rmb) + item.getAmount().toString());
        } else {
            helper.setText(R.id.tv_amount, "");
        }
    }
}
