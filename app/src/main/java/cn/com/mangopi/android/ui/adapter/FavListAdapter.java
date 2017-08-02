package cn.com.mangopi.android.ui.adapter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public class FavListAdapter extends QuickAdapter<FavBean> {

    public FavListAdapter(Context context, int layoutResId, List<FavBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, FavBean item) {
        helper.setImageBuilder(R.id.iv_avatar, item.getLogo_rsurl(), Application.application.getDefaultOptions())
                .setText(R.id.tv_title, item.getEntity_name());
    }
}
