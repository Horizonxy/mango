package cn.com.mangopi.android.ui.adapter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public class MessageListAdapter extends QuickAdapter<MessageBean> {

    public MessageListAdapter(Context context, int layoutResId, List<MessageBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, MessageBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getRemark());
    }
}
