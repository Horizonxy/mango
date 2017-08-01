package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.ContentDetailBean;

public interface ContentDetailListener extends BaseViewListener {

    void onSuccess(ContentDetailBean contentDetail);
    long getId();
}
