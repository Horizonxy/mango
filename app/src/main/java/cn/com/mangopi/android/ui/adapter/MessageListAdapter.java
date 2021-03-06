package cn.com.mangopi.android.ui.adapter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.DateUtils;

public class MessageListAdapter extends QuickAdapter<MessageBean> {



    public MessageListAdapter(Context context, int layoutResId, List<MessageBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MessageBean item) {
        helper.setText(R.id.tv_title, item.getSend_user_name())
                .setText(R.id.tv_content, item.getShowContent())
                .setText(R.id.tv_time, DateUtils.getShowTime(item.getCreate_time()))
                .setVisible(R.id.tv_num, item.getState() != null && item.getState().intValue() != 1 && item.getState().intValue() != -2);

        helper.setVisible(R.id.v_line, helper.getPosition() < (data.size() - 1));
    }
}
