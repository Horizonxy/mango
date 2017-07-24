package com.mango.di.module;

import android.support.v4.app.Fragment;

import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.di.Type;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.data.CourseModel;
import com.mango.presenter.TeacherPresenter;
import com.mango.ui.adapter.RecommendCourseAdapter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.TeacherListener;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class TeacherFragmentModule {

    Fragment fragment;
    List<CourseBean> listDatas;
    List<CourseClassifyBean> gridDatas;

    public TeacherFragmentModule( Fragment fragment, List<CourseBean> listDatas, List<CourseClassifyBean> gridDatas) {
        this.fragment = fragment;
        this.listDatas = listDatas;
        this.gridDatas = gridDatas;
    }

    @FragmentScope
    @Provides
    @Type("list")
    public QuickAdapter provideListQuickAdapter(){
        return new RecommendCourseAdapter(fragment.getContext(), R.layout.listview_item_recommend_teacher_class, listDatas){};
    }


    @FragmentScope
    @Provides
    @Type("grid")
    public QuickAdapter provideGridQuickAdapter(){
        return new QuickAdapter<CourseClassifyBean>(fragment.getContext(), R.layout.gridview_item_class_category, gridDatas) {
            @Override
            protected void convert(BaseAdapterHelper helper, CourseClassifyBean item) {

                if(helper.getPosition() == 7){
                    helper.setImageResource(R.id.iv_logo, R.drawable.daoshi_08);
                } else {
                    helper.setImageUrl(R.id.iv_logo, item.getLogo_rsurl());
                }
                helper.setText(R.id.tv_name, item.getClassify_name());

            }
        };
    }

    @FragmentScope
    @Provides
    public TeacherPresenter provideTeacherPresenter(){
        return new TeacherPresenter(new CourseModel(), (TeacherListener) fragment);
    }
}
