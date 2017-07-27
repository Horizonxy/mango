package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;

import java.util.List;

public interface MemeberWalletListener extends BaseViewListener {

    void onWalletSuccess(MemberWalletBean memberWallet);
    void onCardListSuccess(List<MemberCardBean> memberCardList);
}
