package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.SearchBean;
import cn.com.mangopi.android.model.data.SearchModel;
import cn.com.mangopi.android.presenter.SearchPresenter;
import cn.com.mangopi.android.ui.adapter.SearchResultAdapter;
import cn.com.mangopi.android.ui.viewlistener.SearchListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.EmptyHelper;

public class SearchActivity extends BaseActivity implements SearchListener, AdapterView.OnItemClickListener {

    @Bind(R.id.listview)
    PullToRefreshListView listView;
    String searchText;
    int pageNo = 1;
    EmptyHelper emptyHelper;
    SearchResultAdapter searchAdapter;
    List<SearchBean> datas = new ArrayList<SearchBean>();
    boolean hasNext = true;
    SearchPresenter presenter;
    @Bind(R.id.tv_search)
    TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = getIntent().getStringExtra(Constants.BUNDLE_TEXT);
        initView();
        presenter = new SearchPresenter(new SearchModel(), this);

    }

    private void initView() {
        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_05);
        emptyHelper.setMessage(R.string.page_no_search);

        tvSearch.setText(searchText);
        listView.setAdapter(searchAdapter = new SearchResultAdapter(this, R.layout.listview_item_search_result, datas));
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
            presenter.fullSearch();
        }
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
    public void onSearchList(List<SearchBean> searchList) {
        if(pageNo == 1){
            datas.clear();
        }

        listView.onRefreshComplete();
        if(hasNext = (searchList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        if(searchList != null) {
            datas.addAll(searchList);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public String getSearchText() {
        return searchText;
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchBean searchBean = (SearchBean) parent.getAdapter().getItem(position);
        int entityTypeId = searchBean.getEntity_type_id();
        Constants.EntityType entityType = Constants.EntityType.get(entityTypeId);
        if(entityType == Constants.EntityType.COURSE){
            ActivityBuilder.startCourseDetailActivity(this, searchBean.getId());
        } else if(entityType == Constants.EntityType.WORKS){
            ActivityBuilder.startWorksProjectDetailActivity(this, searchBean.getId());
        } else if(entityType == Constants.EntityType.MEMBER){
            ActivityBuilder.startTutorDetailActivity(this, searchBean.getId());
        } else if(entityType == Constants.EntityType.TREND){
            ActivityBuilder.startTrendCommentsActivity(this, searchBean.getId());
        }
    }

    @OnClick(R.id.tv_right)
    void onBack(View v){
        finish();
    }

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }
}
