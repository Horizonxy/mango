package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.TutorBean;

public interface TutorDetailListener extends BaseViewListener {

    long getId();
    void onSuccess(TutorBean tutor);
}
