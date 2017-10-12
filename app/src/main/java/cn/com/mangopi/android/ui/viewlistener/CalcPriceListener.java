package cn.com.mangopi.android.ui.viewlistener;

import java.util.Map;

import cn.com.mangopi.android.model.bean.CalcPriceBean;

public interface CalcPriceListener extends BaseViewListener {

    Map<String, Object> getCalcMap();
    void onCalcSuccess(CalcPriceBean calcPrice);
    void onCalcFailure(String message);
}
