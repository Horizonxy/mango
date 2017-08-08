package cn.com.mangopi.android.di.module;

import android.support.v4.app.Fragment;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.di.Type;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.presenter.TeacherPresenter;
import cn.com.mangopi.android.ui.adapter.RecommendCourseAdapter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.TeacherListener;
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

                if(helper.getPosition() == (gridDatas.size() - 1)){
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
