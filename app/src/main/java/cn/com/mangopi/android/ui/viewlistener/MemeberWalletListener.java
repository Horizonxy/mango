package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;

public interface MemeberWalletListener extends BaseViewListener {

    void onWalletSuccess(MemberWalletBean memberWallet);
    void onCardListSuccess(List<MemberCardBean> memberCardList);
    void onDelSuccess(MemberCardBean card);
}
