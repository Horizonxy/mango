package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.BulletinBean;

public interface BulletinDetailListener extends BaseViewListener {

    void onSuccess(BulletinBean bulletin);
    long getId();
}

