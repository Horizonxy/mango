package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.TrendDetailBean;

public interface TrendCommentsListener extends BaseViewListener {
    void onTrendSuccess(TrendDetailBean trendDetail);
    long getId();
}
