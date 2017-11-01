package cn.com.mangopi.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.SearchBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public class SearchResultAdapter extends QuickAdapter<SearchBean> {

    DisplayImageOptions options;

    public SearchResultAdapter(Context context, int layoutResId, List<SearchBean> data) {
        super(context, layoutResId, data);
        this.options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnLoading(R.mipmap.logo)
                .showImageForEmptyUri(R.mipmap.logo)
                .showImageOnFail(R.mipmap.logo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    protected void convert(BaseAdapterHelper helper, SearchBean item) {
        helper.setImageResource(R.id.iv_avatar, 0)
                .setImageBuilder(R.id.iv_avatar, item.getLogo_rsurl(), options)
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_intro, item.getIntro())
                .setVisible(R.id.v_line, helper.getPosition() < (data.size() - 1));
    }
}
