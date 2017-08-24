package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.CompanyDetailBean;

public interface CompanyListener extends BaseViewListener {

    void onCompanySuccess(CompanyDetailBean companyDetail);
    String getCompanyNo();
}
