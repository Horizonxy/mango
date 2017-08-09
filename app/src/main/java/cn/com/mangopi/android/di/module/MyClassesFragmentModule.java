package cn.com.mangopi.android.di.module;

import android.app.Activity;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@Module
public class MyClassesFragmentModule {

    Activity activity;
    List datas;

    public MyClassesFragmentModule(Activity activity, List datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<CourseBean>(activity, R.layout.listview_item_class_list, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, CourseBean item) {
                helper.setText(R.id.tv_course_title, item.getCourse_title())
                        .setText(R.id.tv_type_method, item.getType_method() + "  |  " + item.getApprove_state_label() + "  |  " + item.getState_label());
                if(item.getSale_price() != null){
                    helper.setVisible(R.id.tv_price, true);
                    helper.setText(R.id.tv_price, activity.getResources().getString(R.string.rmb) + item.getSale_price().toString());
                } else {
                    helper.setVisible(R.id.tv_price, false);
                }
            }
        };
    }
}
