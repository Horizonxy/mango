package cn.com.mangopi.android.ui.adapter;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public class FavListAdapter extends QuickAdapter<FavBean> {

    DisplayImageOptions options;

    public FavListAdapter(Context context, int layoutResId, List<FavBean> data) {
        super(context, layoutResId, data);
        this.options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.logo)
                .showImageForEmptyUri(R.mipmap.logo)
                .build();
    }

    @Override
    protected void convert(BaseAdapterHelper helper, FavBean item) {
        helper.setImageBuilder(R.id.iv_avatar, item.getLogo_rsurl(), options)
                .setText(R.id.tv_title, item.getEntity_name());
        helper.setVisible(R.id.v_line, helper.getPosition() < (data.size() - 1));
    }
}
