package com.mango.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.data.CourseModel;
import com.mango.presenter.TeacherPresenter;
import com.mango.ui.adapter.RecommendCourseAdapter;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.TeacherListener;
import com.mango.ui.widget.MangoPtrFrameLayout;
import com.mango.util.ActivityBuilder;
import com.mango.util.EmptyHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassListFragment extends BaseFragment implements AdapterView.OnItemClickListener, TeacherListener, EmptyHelper.OnRefreshListener {

    MangoPtrFrameLayout refreshLayout;
    ListView listView;

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
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), this);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter = new RecommendCourseAdapter(getContext(), R.layout.listview_item_recommend_teacher_class, datas));
        listView.setOnItemClickListener(this);
        refreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                hasNext = true;
                loadData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                pageNo++;
                loadData();
            }
        });
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh(true);
            }
        }, 400);
    }

    private void loadData() {
        if(hasNext) {
            presenter.getCourseList(0);
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
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
            if (pageNo == 1) {
                refreshLayout.refreshComplete();
            }
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(refreshLayout);
        } else {
            emptyHelper.hideEmptyView(refreshLayout);
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
            refreshLayout.refreshComplete();
        }

        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
        if (hasNext = (datas.size() >= Constants.PAGE_SIZE)) {
            pageNo++;
        } else {
            refreshLayout.setNoMoreData();
        }

        datas.addAll(courseList);

        adapter.notifyDataSetChanged();

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(refreshLayout);
        } else {
            emptyHelper.hideEmptyView(refreshLayout);
            adapter.notifyDataSetChanged();
        }
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
