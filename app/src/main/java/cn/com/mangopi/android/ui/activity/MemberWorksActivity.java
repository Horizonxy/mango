package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.model.data.MessageModel;
import cn.com.mangopi.android.presenter.MessagePresenter;
import cn.com.mangopi.android.presenter.WorksProjectPresenter;
import cn.com.mangopi.android.ui.adapter.MessageListAdapter;
import cn.com.mangopi.android.ui.adapter.WorksProjectListAdapter;
import cn.com.mangopi.android.ui.viewlistener.WorksProjectListListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.EmptyHelper;
import cn.com.mangopi.android.util.MangoUtils;

/**
 * 我的工作包
 */
public class MemberWorksActivity extends BaseTitleBarActivity implements WorksProjectListListener, AdapterView.OnItemClickListener {

    int relation;
    @Bind(R.id.listview)
    PullToRefreshListView listView;
    int pageNo = 1;
    boolean hasNext = true;
    List<ProjectListBean> datas = new ArrayList<ProjectListBean>();
    EmptyHelper emptyHelper;
    WorksProjectPresenter projectPresenter;
    WorksProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relation = getIntent().getIntExtra(Constants.BUNDLE_WORKS_RELATION, 0);
        if(relation == 0){
            finish();
        }
        setContentView(R.layout.layout_pull_listview);

        initView();

        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
//        emptyHelper.setImageRes(R.drawable.null_msg);
        emptyHelper.showMessage(false);
        emptyHelper.showRefreshButton(false);
        projectPresenter = new WorksProjectPresenter(this);
        loadData();
    }

    private void loadData() {
        if(hasNext) {
            projectPresenter.getProjectList();
        }
    }

    private void initView() {
        if(relation == 1) {
            titleBar.setTitle(R.string.my_join_works);
        } else if(relation == 2){
            titleBar.setTitle(R.string.my_company_works);
        }

        listView.setAdapter(adapter = new WorksProjectListAdapter(this, R.layout.listview_item_member_works_project, datas, relation));
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
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            listView.onRefreshComplete();

            if(datas == null || datas.size() == 0){
                emptyHelper.showEmptyView(listView);
            } else {
                emptyHelper.hideEmptyView(listView);
            }
        }
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public int getRelation() {
        return relation;
    }

    @Override
    public void onProjectListSuccess(List<ProjectListBean> projectList) {
        if(pageNo == 1){
            datas.clear();
            listView.onRefreshComplete();
        }

        if(hasNext = (projectList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        datas.addAll(projectList);

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onDestroy() {
        if(projectPresenter != null){
            projectPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
