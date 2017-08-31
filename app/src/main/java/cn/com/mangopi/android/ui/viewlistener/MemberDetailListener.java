package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.MemberBean;

/**
 * @author 蒋先明
 * @date 2017/7/7
 */

public interface MemberDetailListener extends BaseViewListener {

    void onSuccess(MemberBean member);
    long getMemberId();
}
