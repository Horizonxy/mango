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

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.data.CourseModel;
import cn.com.mangopi.android.presenter.TeacherPresenter;
import cn.com.mangopi.android.ui.adapter.RecommendCourseAdapter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.TeacherListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.EmptyHelper;

public class ClassListFragment extends BaseFragment implements AdapterView.OnItemClickListener, TeacherListener, EmptyHelper.OnRefreshListener {

    PullToRefreshListView listView;

    int pageNo = 1;
    List datas = new ArrayList();
    QuickAdapter adapter;
    boolean hasNext = true;
    CourseClassifyBean classify;
    TeacherPresenter presenter;
    EmptyHelper emptyHelper;

    public static ClassListFragment newInstance(CourseClassifyBean classify){
        ClassListFragment fragment = new ClassListFragment();
        Bundle bundle =  new Bundle();
        bundle.putSerializable("classify", classify);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classify = (CourseClassifyBean) getArguments().getSerializable("classify");
        presenter = new TeacherPresenter(new CourseModel(), this);
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), this);
        emptyHelper.setImageRes(R.drawable.page_icon_06);
        emptyHelper.setMessage(R.string.page_no_data);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter = new RecommendCourseAdapter(getContext(), R.layout.listview_item_recommend_teacher_class, datas));
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

    private void loadData() {
        if(hasNext) {
            presenter.getCourseList(0);
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseBean course = (CourseBean) parent.getAdapter().getItem(position);
        ActivityBuilder.startCourseDetailActivity(getActivity(), course.getId());
    }

    @Override
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            listView.onRefreshComplete();
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onCourseListSuccess(int hotTypes, List<CourseBean> courseList) {
        if (pageNo == 1) {
            datas.clear();
        }

        listView.onRefreshComplete();
        if (hasNext = (datas.size() >= Constants.PAGE_SIZE)) {
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        if(courseList != null) {
            datas.addAll(courseList);
        }

        adapter.notifyDataSetChanged();

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> classifyList) {}

    @Override
    public Map<String, Object> getQueryMap(int hotTypes) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("state", 50);
        map.put("page_no", pageNo);
        map.put("page_size", Constants.PAGE_SIZE);
        map.put("classify_id", classify.getId());
        return map;
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        hasNext = true;
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
