package cn.com.mangopi.android.di.module;

import android.app.Activity;

import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberPresenter;
import cn.com.mangopi.android.ui.viewlistener.SetNickNameListener;

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
