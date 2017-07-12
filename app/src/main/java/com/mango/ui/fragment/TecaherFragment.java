package com.mango.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.Constants;
import com.mango.R;
import com.mango.di.Type;
import com.mango.di.component.DaggerTeacherFragmentComponent;
import com.mango.di.module.TeacherFragmentModule;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.presenter.TeacherPresenter;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.TeacherListener;
import com.mango.ui.widget.GridView;
import com.mango.ui.widget.MangoPtrFrameLayout;
import com.mango.util.ActivityBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class TecaherFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, TeacherListener {

    MangoPtrFrameLayout refreshLayout;
    ListView listView;
    int pageNo = 1;
    List<CourseBean> listDatas = new ArrayList<CourseBean>();
    @Inject
    @Type("list")
    QuickAdapter listAdapter;
    List<CourseClassifyBean> gridDatas = new ArrayList<CourseClassifyBean>();
    GridView gvCategory;
    @Inject
    @Type("grid")
    QuickAdapter gridAdapter;
    @Inject
    TeacherPresenter presenter;
    boolean hasNext = true;

    public TecaherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTeacherFragmentComponent.builder().teacherFragmentModule(new TeacherFragmentModule(this, listDatas, gridDatas)).build().inject(this);
    }

    @Override
    void findView(View root) {
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        root.findViewById(R.id.tv_left).setOnClickListener(this);
        root.findViewById(R.id.tv_right).setOnClickListener(this);
    }

    @Override
    void initView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_header_teacher, null, false);
        listView.addHeaderView(headerView);
        gvCategory = (GridView) headerView.findViewById(R.id.gv_category);
        gvCategory.setAdapter(gridAdapter);
        loadCategory();

        listView.setAdapter(listAdapter);
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

    private void loadCategory(){
        presenter.getCourseClassify();
    }

    private void loadData() {
        if(hasNext) {
            presenter.getCourseList(1);
            presenter.getCourseList(2);
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_tecaher;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                ActivityBuilder.startTeacherClassCategoryActivity(getActivity());
                break;
            case R.id.tv_right:
                ActivityBuilder.startMyClassesActivity(getActivity());
                break;
        }
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
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onCourseListSuccess(int hotTypes, List<CourseBean> courseList) {
        if(hotTypes == 2) {
            if (pageNo == 1) {
                listDatas.clear();
                refreshLayout.refreshComplete();
            }

            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            if (hasNext = (listDatas.size() >= Constants.PAGE_SIZE)) {
                pageNo++;
            } else {
                refreshLayout.setNoMoreData();
            }

            listDatas.addAll(courseList);

            listAdapter.notifyDataSetChanged();
        } else if(hotTypes == 1){

        }
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> classifyList) {
        gridDatas.clear();
        gridDatas.addAll(classifyList);
        gridAdapter.notifyDataSetChanged();
    }
}
