package cn.com.mangopi.android.ui.viewlistener;

import java.math.BigDecimal;

public interface WalletDrawListener extends BaseViewListener {

    void onDrawSuccess();
    long getCardId();
    BigDecimal getAmount();
}
