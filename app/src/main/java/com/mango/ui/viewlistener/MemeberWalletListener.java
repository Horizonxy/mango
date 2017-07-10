package com.mango.ui.viewlistener;

import com.mango.model.bean.MemberCardBean;
import com.mango.model.bean.MemberWalletBean;

import java.util.List;

public interface MemeberWalletListener extends BaseViewListener {

    void onWalletSuccess(MemberWalletBean memberWallet);
    void onCardListSuccess(List<MemberCardBean> memberCardList);
}
