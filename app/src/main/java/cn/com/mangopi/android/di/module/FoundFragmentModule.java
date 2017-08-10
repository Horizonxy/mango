package cn.com.mangopi.android.di.module;

import android.support.v4.app.Fragment;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.model.data.PraiseModel;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.presenter.FoundPresenter;
import cn.com.mangopi.android.ui.adapter.TrendListAdapter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.FavListener;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@Module
public class FoundFragmentModule {

    Fragment fragment;
    List datas;


    public FoundFragmentModule(Fragment fragment, List datas) {
        this.fragment = fragment;
        this.datas = datas;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new TrendListAdapter(fragment.getActivity(), R.layout.listview_item_found, datas, (FoundListener) fragment);
    }

    @FragmentScope
    @Provides
    public FoundPresenter provideFoundPresenter(){
        return new FoundPresenter(new PraiseModel(), new TrendModel(), (FoundListener) fragment);
    }

    @FragmentScope
    @Provides
    public FavPresenter provideFavPresenter(){
        return new FavPresenter(new FavModel(), (FavListener) fragment);
    }
}
