package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.CommunityClassifyBean;
import cn.com.mangopi.android.model.bean.CommunityTypeBean;

public interface CommunityListener extends BaseViewListener {

    void onTypeListSuccess(List<CommunityTypeBean> communityTypeList);
    void onClassifyListSuccess(List<CommunityClassifyBean> communityClassifyList);

}
