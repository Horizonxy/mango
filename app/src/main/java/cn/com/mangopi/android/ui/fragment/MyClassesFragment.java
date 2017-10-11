package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerMyClassesFragmentComponent;
import cn.com.mangopi.android.di.module.MyClassesFragmentModule;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.presenter.CourseListPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.CourseListListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.EmptyHelper;

public class MyClassesFragment extends BaseFragment implements AdapterView.OnItemClickListener,CourseListListener,MyClassesFragmentModule.CourseStateListener {

    public static final int TPYE_ALL = 0;
    public static final int TPYE_ON = 1;

    PullToRefreshListView listView;

    int pageNo = 1;
    List datas = new ArrayList();
    @Inject
    QuickAdapter adapter;
    boolean hasNext = true;
    EmptyHelper emptyHelper;
    CourseListPresenter presenter;
    int type;

    public MyClassesFragment() {
    }
    public static MyClassesFragment newInstance(int type) {
        MyClassesFragment fragment = new MyClassesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMyClassesFragmentComponent.builder().myClassesFragmentModule(new MyClassesFragmentModule(getActivity(), datas, this)).build().inject(this);
        type = getArguments().getInt("type");

        presenter = new CourseListPresenter(new CourseModel(), this);
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_07);
        emptyHelper.setMessage(R.string.page_no_course);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 1;
                hasNext = true;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo++;
                loadData();
            }
        });
        listView.setRefreshing(true);
    }

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    private void loadData() {
        if(hasNext) {
            presenter.getCourseList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseBean item = (CourseBean) parent.getAdapter().getItem(position);
        ActivityBuilder.startCourseDetailActivity(getActivity(), item.getId());
    }

    @Override
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            listView.onRefreshComplete();

            if(datas == null || datas.size() == 0){
                emptyHelper.showEmptyView(listView);
            } else {
                emptyHelper.hideEmptyView(listView);
            }
        } else {
            AppUtils.showToast(getContext(), message);
        }
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onSuccess(List<CourseBean> courseList) {
        if(pageNo == 1){
            datas.clear();
        }

        listView.onRefreshComplete();
        if(hasNext = (courseList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        if(courseList != null) {
            datas.addAll(courseList);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("is_self", 1);
        map.put("page_no", pageNo);
        map.put("page_size", Constants.PAGE_SIZE);
        if(type == TPYE_ON){
            map.put("state", 50);
        }
        return map;
    }


    @Override
    public void onDelSuccess(CourseBean course) {
        datas.remove(course);
        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onOffSuccess(CourseBean course) {
//        course.setState(0);
//        course.setState_label("停用");
//        course.setOff_approve_state(50);
//        adapter.notifyDataSetChanged();
        AppUtils.showToast(getContext(), "您的下架请求已提交到后台，请耐心等待管理人员的审核");
    }

    @Override
    public void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onStateChanged(CourseBean course, int state) {
        //上50 下0 架
        if(state == 0){
            presenter.offCourse(course);
        }
    }

    @Override
    public void onDelCourse(CourseBean course) {
        presenter.delCourse(course);
    }
}
