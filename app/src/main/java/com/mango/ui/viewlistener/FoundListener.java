package com.mango.ui.viewlistener;

import com.mango.model.bean.TrendBean;

import java.util.List;

public interface FoundListener extends BaseViewListener {

    void onSuccess(List<TrendBean> trendList);
    int getPageNo();
}
