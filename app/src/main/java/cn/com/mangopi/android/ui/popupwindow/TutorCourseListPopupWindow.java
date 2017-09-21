package cn.com.mangopi.android.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DisplayUtils;

public class TutorCourseListPopupWindow extends BasePopupWindow {

    List<CourseBean> courseList;
    Context context;
    int selectedPos;
    TextView selectedView;

    public TutorCourseListPopupWindow(Context context, List<CourseBean> courseList) {
        super(context);
        this.courseList = courseList;
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow_tutor_course, null, false);
        setContentView(view);
        Button btnSure = (Button) view.findViewById(R.id.tv_sure);
        ListView lvCourse = (ListView) view.findViewById(R.id.lv_course);
        lvCourse.setDivider(null);
        btnSure.setEnabled(false);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);
        setAnimationStyle(R.style.PopupWindowBottomInOut);

//        for (int i = 0; i < courseList.size(); i++){
//            View item = LayoutInflater.from(context).inflate(R.layout.listview_item_course_in_popup, layoutCourse, false);
//            TextView tvPrice = (TextView) item.findViewById(R.id.tv_price);
//            TextView tvTitle = (TextView) item.findViewById(R.id.tv_title);
//            if(i == (courseList.size() - 1)){
//                item.findViewById(R.id.v_line).setVisibility(View.GONE);
//            }
//            CourseBean course = courseList.get(i);
//            tvTitle.setText(course.getCourse_title());
//            if(course.getSale_price() != null){
//                tvPrice.setText(context.getString(R.string.rmb) + course.getSale_price().toString());
//            }
//            layoutCourse.addView(item);
//        }
        lvCourse.setAdapter(new QuickAdapter<CourseBean>(context, R.layout.listview_item_course_in_popup, courseList) {
            @Override
            protected void convert(BaseAdapterHelper helper, CourseBean item) {
                helper.setText(R.id.tv_title, item.getCourse_title());
                if(item.getSale_price() != null){
                    helper.setText(R.id.tv_price, context.getString(R.string.rmb) + item.getSale_price().toString());
                }
                if(helper.getPosition() == (courseList.size() - 1)){
                    helper.setVisible(R.id.v_line, false);
                } else {
                    helper.setVisible(R.id.v_line, true);
                }
            }
        });
        lvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedView != null){
                    selectedView.setSelected(false);
                }
                selectedView = (TextView) view.findViewById(R.id.tv_title);
                selectedView.setSelected(true);
                selectedPos = position;
                btnSure.setEnabled(true);
            }
        });

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvCourse.getLayoutParams();
        if(params.height > DisplayUtils.screenHeight(context) / 2) {
            params.height = DisplayUtils.screenHeight(context) / 2;
            lvCourse.setLayoutParams(params);
        }

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ActivityBuilder.startPlaceOrderActivity((Activity) context, convertCourseDetail(courseList.get(selectedPos)));
            }
        });
    }

    private CourseDetailBean convertCourseDetail(CourseBean course){
        CourseDetailBean courseDetail = new CourseDetailBean();
        courseDetail.setId(course.getId());
        courseDetail.setCourse_title(course.getCourse_title());
        courseDetail.setMember_id(course.getMember_id());
        courseDetail.setMember_name(course.getMember_name());
        courseDetail.setSale_price(course.getSale_price());
        courseDetail.setType_name(course.getType_name());
        courseDetail.setAvatar_rsurl(course.getAvatar_rsurl());
        courseDetail.setCity(course.getCity());
        courseDetail.setCourse_content(course.getCourse_content());
        return  courseDetail;
    }
}
