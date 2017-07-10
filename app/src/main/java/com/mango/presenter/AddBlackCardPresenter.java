package com.mango.presenter;

import com.mango.model.data.MemberModel;
import com.mango.ui.viewlistener.AddBlankCardListener;

public class AddBlackCardPresenter extends BasePresenter {

    MemberModel memberModel;
    AddBlankCardListener listener;

    public AddBlackCardPresenter(MemberModel memberModel, AddBlankCardListener listener) {
        this.memberModel = memberModel;
        this.listener = listener;
    }

    public void addBlankCard(){

    }
}
