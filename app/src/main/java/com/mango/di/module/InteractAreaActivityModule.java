package com.mango.di.module;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.mango.R;
import com.mango.di.ActivityScope;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/7/5
 */
@Module
public class InteractAreaActivityModule {

    Activity activity;
    List datas;

    public InteractAreaActivityModule(Activity activity, List datas) {
        this.activity = activity;
        this.datas = datas;
    }


    @ActivityScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<String>(activity, R.layout.listview_item_interact_area, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.getView(R.id.iv_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "comment: " + item, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }
}
