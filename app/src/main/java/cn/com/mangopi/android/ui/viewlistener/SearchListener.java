package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.SearchBean;

public interface SearchListener extends BaseViewListener {

    void onSearchList(List<SearchBean> searchList);
    String getSearchText();
    int getPageNo();
}
