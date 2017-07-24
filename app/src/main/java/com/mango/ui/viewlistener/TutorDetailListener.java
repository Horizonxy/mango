package com.mango.ui.viewlistener;

import com.mango.model.bean.TutorBean;

public interface TutorDetailListener extends BaseViewListener {

    long getId();
    void onSuccess(TutorBean tutor);
}
