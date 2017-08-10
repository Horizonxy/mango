package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

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
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.EmptyHelper;

public class MyClassesFragment extends BaseFragment implements AdapterView.OnItemClickListener,CourseListListener,MyClassesFragmentModule.CourseStateListener {

    public static final int TPYE_ALL = 0;
    public static final int TPYE_ON = 1;

    MangoPtrFrameLayout refreshLayout;
    ListView listView;

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
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), null);
        emptyHelper.showRefreshButton(false);
        emptyHelper.showMessage(false);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.dp_10));
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

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    private void loadData() {
        if(hasNext) {
            presenter.getCourseList();
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
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
            if (pageNo == 1) {
                refreshLayout.refreshComplete();
            }
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);

            if(datas == null || datas.size() == 0){
                emptyHelper.showEmptyView(refreshLayout);
            } else {
                emptyHelper.hideEmptyView(refreshLayout);
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
            refreshLayout.refreshComplete();
        }

        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
        if(hasNext = (courseList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
        } else {
            refreshLayout.setNoMoreData();
        }

        datas.addAll(courseList);

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(refreshLayout);
        } else {
            emptyHelper.hideEmptyView(refreshLayout);
            adapter.notifyDataSetChanged();
        }
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
            emptyHelper.showEmptyView(refreshLayout);
        } else {
            emptyHelper.hideEmptyView(refreshLayout);
            adapter.notifyDataSetChanged();
        }
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
        //上下架
    }

    @Override
    public void onDelCourse(CourseBean course) {
        presenter.delCourse(course);
    }
}
