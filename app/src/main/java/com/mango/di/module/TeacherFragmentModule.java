package com.mango.di.module;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.util.ActivityBuilder;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class TeacherFragmentModule {

    Activity activity;
    List datas;

    public TeacherFragmentModule(Activity activity, List datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<String>(activity, R.layout.listview_item_recommend_teacher_class, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        };
    }

}
