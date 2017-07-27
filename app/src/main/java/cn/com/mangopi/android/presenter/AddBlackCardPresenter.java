package cn.com.mangopi.android.presenter;

import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.ui.viewlistener.AddBlankCardListener;

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
