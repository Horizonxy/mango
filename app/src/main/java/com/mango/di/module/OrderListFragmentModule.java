package com.mango.di.module;

import android.app.Activity;

import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@Module
public class OrderListFragmentModule {

    Activity activity;
    List datas;

    public OrderListFragmentModule(Activity activity, List datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<String>(activity, R.layout.listview_item_order_list, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        };
    }

}
