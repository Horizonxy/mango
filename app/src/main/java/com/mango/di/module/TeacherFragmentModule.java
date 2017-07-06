package com.mango.di.module;

import android.app.Activity;

import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.di.Type;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class TeacherFragmentModule {

    Activity activity;
    List listDatas;
    List gridDatas;

    public TeacherFragmentModule(Activity activity, List listDatas, List gridDatas) {
        this.activity = activity;
        this.listDatas = listDatas;
        this.gridDatas = gridDatas;
    }

    @FragmentScope
    @Provides
    @Type("list")
    public QuickAdapter provideListQuickAdapter(){
        return new QuickAdapter<String>(activity, R.layout.listview_item_recommend_teacher_class, listDatas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        };
    }


    @FragmentScope
    @Provides
    @Type("grid")
    public QuickAdapter provideGridQuickAdapter(){
        return new QuickAdapter<String>(activity, R.layout.gridview_item_class_category, gridDatas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        };
    }
}
