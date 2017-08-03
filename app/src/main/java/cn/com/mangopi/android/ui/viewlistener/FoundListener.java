package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.bean.TrendBean;

public interface FoundListener extends BaseViewListener {

    void onSuccess(List<TrendBean> trendList);
    int getPageNo();
    void praise(TrendBean trend);
    void notifyData();
    void onFailure();
    void delOrAddFav(TrendBean trend);
    Map<String, Object> getQueryMap();
}
