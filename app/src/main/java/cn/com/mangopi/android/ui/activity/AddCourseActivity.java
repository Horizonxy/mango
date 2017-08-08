package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseTypeBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.presenter.AddCoursePresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.popupwindow.ListViewPopupWindow;
import cn.com.mangopi.android.ui.viewlistener.AddCourseLisetener;
import cn.com.mangopi.android.util.AppUtils;

public class AddCourseActivity extends BaseTitleBarActivity implements AddCourseLisetener {

    AddCoursePresenter addPresenter;
    List<CourseTypeBean> typeList = new ArrayList<>();
    List<CourseClassifyBean> classifyList = new ArrayList<>();
    @Bind(R.id.tv_class)
    TextView tvClassify;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.tv_each_time)
    TextView tvEachTime;
    List<String> serviceTimeList = new ArrayList<>();
    List<String> eachTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        initView();
        addPresenter = new AddCoursePresenter(new CourseModel(), this);

        serviceTimeList.add("一次");
        serviceTimeList.add("二次");
        serviceTimeList.add("三次");

        eachTimeList.add("每次约1小时");
        eachTimeList.add("每次约2小时");
        eachTimeList.add("每次约3小时");
        eachTimeList.add("每次约4小时");
        eachTimeList.add("每次约8小时");
        eachTimeList.add("每次约12小时");
    }

    private void initView() {
        titleBar.setTitle(R.string.add_course);
    }

    @OnClick(R.id.tv_class)
    void onClassifyClick(View v){
        if(classifyList.size() == 0){
            addPresenter.getClassify();
        } else {
            showClassifyList(classifyList);
        }
    }

    private void showClassifyList(List<CourseClassifyBean> classifyList){
        if(classifyList.size() > 0) {
            ListViewPopupWindow<CourseClassifyBean> classifyPopupWindow = new ListViewPopupWindow<CourseClassifyBean>(this, classifyList, (CourseClassifyBean) tvClassify.getTag(), new ListViewPopupWindow.OnListViewListener<CourseClassifyBean>() {
                @Override
                public void onItemClick(CourseClassifyBean data) {
                    tvClassify.setText(data.getClassify_name());
                    tvClassify.setTag(data);
                }
            }){

                @Override
                public void fiddData(BaseAdapterHelper helper, CourseClassifyBean item) {
                    helper.setText(R.id.tv_text, item.getClassify_name());
                }
            };
            classifyPopupWindow.showAsDropDown(tvClassify);
        } else {
            onFailure("无课程分类");
        }
    }

    @OnClick(R.id.tv_type)
    void onTypeClick(View v){
        if(typeList.size() == 0){
            addPresenter.getTypeList();
        } else {
            showTypeList(typeList);
        }
    }

    private void showTypeList(List<CourseTypeBean> typeList){
        if(typeList.size() > 0) {
            ListViewPopupWindow<CourseTypeBean> typePopupWindow = new ListViewPopupWindow<CourseTypeBean>(this, typeList, (CourseTypeBean) tvType.getTag(), new ListViewPopupWindow.OnListViewListener<CourseTypeBean>() {
                @Override
                public void onItemClick(CourseTypeBean data) {
                    tvType.setText(data.getType());
                    tvType.setTag(data);
                }
            }){

                @Override
                public void fiddData(BaseAdapterHelper helper, CourseTypeBean item) {
                    helper.setText(R.id.tv_text, item.getType());
                }
            };
            typePopupWindow.showAsDropDown(tvType);
        } else {
            onFailure("无课程类型");
        }
    }

    @OnClick(R.id.tv_each_time)
    void onEachTimeClick(View v){
        String data = (String) tvEachTime.getTag();
        ListViewPopupWindow<String> typePopupWindow = new ListViewPopupWindow<String>(this, eachTimeList, data == null ? eachTimeList.get(0) : data, new ListViewPopupWindow.OnListViewListener<String>() {
            @Override
            public void onItemClick(String data) {
                tvEachTime.setText(data);
                tvEachTime.setTag(data);
            }
        }){

            @Override
            public void fiddData(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_text, item);
            }
        };
        typePopupWindow.showAsDropDown(tvEachTime);
    }

    @OnClick(R.id.tv_service_time)
    void onServiceTimeClick(View v){
        String data = (String) tvServiceTime.getTag();
        ListViewPopupWindow<String> typePopupWindow = new ListViewPopupWindow<String>(this, serviceTimeList, data == null ? serviceTimeList.get(0) : data, new ListViewPopupWindow.OnListViewListener<String>() {
            @Override
            public void onItemClick(String data) {
                tvServiceTime.setText(data);
                tvServiceTime.setTag(data);
            }
        }){

            @Override
            public void fiddData(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_text, item);
            }
        };
        typePopupWindow.showAsDropDown(tvServiceTime);
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> classifyList) {
        this.classifyList.clear();
        this.classifyList.addAll(classifyList);
        showClassifyList(this.classifyList);
    }

    @Override
    public void onTypeSuccess(List<CourseTypeBean> typeList) {
        this.typeList.clear();
        this.typeList.addAll(typeList);
        showTypeList(typeList);
    }

    @Override
    protected void onDestroy() {
        if(addPresenter != null){
            addPresenter.onDestroy();
        }
        super.onDestroy();
    }

}
