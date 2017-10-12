package cn.com.mangopi.android.ui.adapter;

import android.content.Context;
import java.util.List;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.DateUtils;

public class CouponListAdapter extends QuickAdapter<CouponBean> {

    public CouponListAdapter(Context context, int layoutResId, List<CouponBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, CouponBean item) {
        helper.setText(R.id.tv_coupon_name, item.getCoupon_name())
            .setText(R.id.tv_coupon_desc, item.getCoupon_desc())
            .setText(R.id.tv_state_label, item.getState_label())
            .setText(R.id.tv_coupon_time, String.format(context.getString(R.string.coupon_use_time),
                    DateUtils.dateToString(item.getStart_time(), DateUtils.DATE_PATTERN),
                    DateUtils.dateToString(item.getEnd_time(), DateUtils.DATE_PATTERN)))
//            .setImageUrl(R.id.iv_logo, "")
            .setBackgroundRes(R.id.layout_item, 50 == item.getState() ? R.drawable.coupon_01 : R.drawable.coupon_02)
            .setVisible(R.id.v_line, helper.getPosition() == (data.size() - 1));
    }
}
