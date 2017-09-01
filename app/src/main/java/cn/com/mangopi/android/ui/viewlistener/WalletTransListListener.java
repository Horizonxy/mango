package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.TransListBean;

public interface WalletTransListListener extends BaseViewListener {

    int getPageNo();
    void onTransListSuccess(List<TransListBean> transList);
}
