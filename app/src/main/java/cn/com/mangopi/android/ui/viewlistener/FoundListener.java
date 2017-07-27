package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.TrendBean;

import java.util.List;

public interface FoundListener extends BaseViewListener {

    void onSuccess(List<TrendBean> trendList);
    int getPageNo();
    void praise(TrendBean trend);
    void notifyData();
    void onFailure();
    void delOrAddFav(TrendBean trend);
}
