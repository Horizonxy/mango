package com.mango.ui.viewlistener;

import com.mango.model.bean.MemberBean;

/**
 * @author 蒋先明
 * @date 2017/7/7
 */

public interface MyFragmentListener extends BaseViewListener {

    void onSuccess(MemberBean member);
    long getMemberId();
}
