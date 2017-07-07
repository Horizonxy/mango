package com.mango.ui.viewlistener;


import com.mango.model.bean.AdvertBean;

import java.util.List;

public interface HomeFragmentListener extends BaseViewListener {

    void onSuccess(String position, List<AdvertBean> advertBean);
    String getUserIdentity();
}
