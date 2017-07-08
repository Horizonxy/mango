package com.mango.di.module;

import android.support.v4.app.Fragment;
import android.view.View;

import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.model.bean.TrendBean;
import com.mango.model.data.TrendModel;
import com.mango.presenter.FoundPresenter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.FoundListener;
import com.mango.util.ActivityBuilder;

import java.util.List;

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

    public FoundFragmentModule( Fragment fragment, List datas) {
        this.fragment = fragment;
        this.datas = datas;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<TrendBean>(fragment.getContext(), R.layout.listview_item_found, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, TrendBean item) {
                helper.setText(R.id.tv_publisher_name, item.getPublisher_name());
                helper.setText(R.id.tv_publish_time_labe, item.getPublish_time_labe());
                helper.setImageUrl(R.id.iv_publisher_avatar, item.getAvatar_rsurl());
                helper.getView(R.id.layout_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityBuilder.startInteractAreaActivity(fragment.getActivity());
                    }
                });
            }
        };
    }

    @FragmentScope
    @Provides
    public FoundPresenter provideFoundPresenter(){
        return new FoundPresenter(new TrendModel(), (FoundListener) fragment);
    }
}
