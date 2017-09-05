package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
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
    @Bind(R.id.et_course_title)
    EditText etTitle;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.layout_classify)
    View layoutClassify;
    @Bind(R.id.layout_type)
    View layoutType;
    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        serviceTimeList.add("一次");
        serviceTimeList.add("二次");
        serviceTimeList.add("三次");

        eachTimeList.add("每次约1小时");
        eachTimeList.add("每次约2小时");
        eachTimeList.add("每次约3小时");
        eachTimeList.add("每次约4小时");
        eachTimeList.add("每次约8小时");
        eachTimeList.add("每次约12小时");

        initView();
        addPresenter = new AddCoursePresenter(new CourseModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.add_course);

        tvServiceTime.setText(serviceTimeList.get(0));
        tvEachTime.setText(eachTimeList.get(0));
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
            classifyPopupWindow.showAsDropDown(layoutClassify);
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
                    tvType.setText(data.getType() + "(" + data.getMethod() + ")");
                    tvType.setTag(data);
                    if(data.getSale_price() != null) {
                        tvPrice.setText(getResources().getString(R.string.rmb) + data.getSale_price().toString());
                    }
                }
            }){

                @Override
                public void fiddData(BaseAdapterHelper helper, CourseTypeBean item) {
                    helper.setText(R.id.tv_text, item.getType() + "(" + item.getMethod() + ")");
                }
            };
            typePopupWindow.showAsDropDown(layoutType);
        } else {
            onFailure("无课程类型");
        }
    }

    @OnClick(R.id.btn_save)
    void onSaveClick(View v){
        if(TextUtils.isEmpty(etTitle.getText())){
            AppUtils.showToast(this, "请输入课程标题");
            return;
        }
        if(tvType.getTag() == null){
            AppUtils.showToast(this, "请选择课程分类");
            return;
        }
        if(tvClassify.getTag() == null){
            AppUtils.showToast(this, "请选择课程类型");
            return;
        }
        if(TextUtils.isEmpty(tvServiceTime.getText()) || TextUtils.isEmpty(tvEachTime.getText())){
            AppUtils.showToast(this, "请选择服务时长");
            return;
        }
        if(TextUtils.isEmpty(etContent.getText())){
            AppUtils.showToast(this, "请输入课程内容");
            return;
        }
        addPresenter.addCourse();
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
    public void onAddCourseSuccess(Object object) {
        if(object != null) {
            AppUtils.showToast(this, "保存课程成功，下拉刷新查看");
        }
    }

    @Override
    public Map<String, Object> getAddMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("course_title", etTitle.getText().toString());
        map.put("type_id", ((CourseTypeBean)tvType.getTag()).getId());
        map.put("service_time", tvServiceTime.getText().toString());
        map.put("each_time", tvEachTime.getText().toString());
        map.put("course_content", etContent.getText().toString());
        map.put("material_rsurls", new String[]{});
        map.put("class_id", ((CourseClassifyBean)tvClassify.getTag()).getId());
        map.put("city", "");
        return map;
    }

    @Override
    protected void onDestroy() {
        if(addPresenter != null){
            addPresenter.onDestroy();
        }
        super.onDestroy();
    }

}
