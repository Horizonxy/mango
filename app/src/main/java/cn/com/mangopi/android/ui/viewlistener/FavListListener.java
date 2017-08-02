package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.FavBean;

public interface FavListListener extends BaseViewListener {

    void onSuccess(List<FavBean> favList);
    int getPageNo();
}
