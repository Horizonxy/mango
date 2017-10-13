package cn.com.mangopi.android.ui.adapter;

import android.content.Context;

import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public class MatchParentPhotoAdapter extends QuickAdapter<String> {

    public MatchParentPhotoAdapter(Context context, int layoutResId, List<String> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, String item) {
        helper.setImageBuilder(R.id.iv_photo, item, Application.application.getDefaultOptions());
    }
}
