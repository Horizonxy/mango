package cn.com.mangopi.android.di.module;

import android.app.Activity;
import android.view.View;

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
    CourseStateListener courseStateListener;

    public MyClassesFragmentModule(Activity activity, List datas, CourseStateListener courseStateListener) {
        this.activity = activity;
        this.datas = datas;
        this.courseStateListener = courseStateListener;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<CourseBean>(activity, R.layout.listview_item_class_list, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, CourseBean item) {
                helper.setText(R.id.tv_course_title, item.getCourse_title());
                String typeMethod = item.getType_name() + " (" + item.getType_method() + ")  |  " + item.getApprove_state_label() + "  |  " + item.getState_label();
                helper.setText(R.id.tv_type_method, typeMethod);

                if(item.getApprove_state() != null && item.getApprove_state().intValue() == 50){//已审核
                    helper.setVisible(R.id.btn_function, false);

//                    if(item.getState() != null && item.getState().intValue() == 0){//下架
//                        helper.setVisible(R.id.btn_function, true);
//                        helper.setText(R.id.btn_function, "上架");
//                    }
                    if(item.getState() != null && item.getState().intValue() == 50){//上架
                        helper.setVisible(R.id.btn_function, true);
                        helper.setText(R.id.btn_function, "下架");
                    }

                    helper.setOnClickListener(R.id.btn_function, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(item != null && item.getState() != null && courseStateListener != null){
                                if(item.getState().intValue() == 0){
                                    courseStateListener.onStateChanged(item, 50);
                                } else if(item.getState().intValue() == 50){
                                    courseStateListener.onStateChanged(item, 0);
                                }
                            }
                        }
                    });
                } else {
                    helper.setVisible(R.id.btn_function, false);
                }

                if(item.getState() != null && item.getState().intValue() == -50){//删除状态
                    helper.setVisible(R.id.btn_del, false);
                } else {
                    helper.setVisible(R.id.btn_del, true);
                    helper.setOnClickListener(R.id.btn_del, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (courseStateListener != null) {
                                courseStateListener.onDelCourse(item);
                            }
                        }
                    });
                }
                if(item.getSale_price() != null){
                    helper.setVisible(R.id.tv_price, true);
                    helper.setText(R.id.tv_price, activity.getResources().getString(R.string.rmb) + item.getSale_price().toString());
                } else {
                    helper.setVisible(R.id.tv_price, false);
                }

                helper.setVisible(R.id.v_divider_line, helper.getPosition() < (datas.size() - 1));
            }
        };
    }

    public interface CourseStateListener {
        void onStateChanged(CourseBean course, int state);
        void onDelCourse(CourseBean course);
    }


}
