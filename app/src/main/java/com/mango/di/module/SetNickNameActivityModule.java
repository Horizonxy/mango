package com.mango.di.module;

import android.app.Activity;

import com.mango.di.ActivityScope;
import com.mango.model.data.MemberModel;
import com.mango.presenter.MemberPresenter;
import com.mango.ui.viewlistener.SetNickNameListener;

import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/7/7
 */
@Module
public class SetNickNameActivityModule {

    Activity activity;

    public SetNickNameActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public MemberPresenter provideMemberPresenter(){
        return new MemberPresenter(new MemberModel(), (SetNickNameListener) activity);
    }
}
