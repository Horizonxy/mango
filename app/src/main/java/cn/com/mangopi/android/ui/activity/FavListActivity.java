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
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.ui.adapter.FavListAdapter;
import cn.com.mangopi.android.ui.viewlistener.FavListListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.EmptyHelper;

public class FavListActivity extends BaseTitleBarActivity implements FavListListener, AdapterView.OnItemClickListener{

    @Bind(R.id.listview)
    PullToRefreshListView listView;
    int pageNo = 1;
    boolean hasNext = true;
    List<FavBean> datas = new ArrayList<FavBean>();
    EmptyHelper emptyHelper;
    FavPresenter favPresenter;
    FavListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);

        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_03);
        emptyHelper.setMessage(R.string.page_no_collection);
        favPresenter = new FavPresenter(new FavModel(), this);
        initView();
    }

    private void loadData() {
        if(hasNext) {
            favPresenter.getFavList();
        }
    }

    private void initView() {
        titleBar.setTitle(R.string.fav);
        listView.setAdapter(adapter = new FavListAdapter(this, R.layout.listview_item_favlist, datas));
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
    public void onSuccess(List<FavBean> favList) {
        if(pageNo == 1){
            datas.clear();
        }

        listView.onRefreshComplete();
        if(hasNext = (favList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        datas.addAll(favList);

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FavBean favBean = (FavBean) parent.getAdapter().getItem(position);
        if(favBean.getEntity_type_id() == Constants.EntityType.COURSE.getTypeId()){
            ActivityBuilder.startCourseDetailActivity(this, favBean.getEntity_id());
        } else if(favBean.getEntity_type_id() == Constants.EntityType.TREND.getTypeId()){
            ActivityBuilder.startTrendCommentsActivity(this, favBean.getEntity_id());
        } else if(favBean.getEntity_type_id() == Constants.EntityType.WORKS.getTypeId()){
            ActivityBuilder.startProjectWorkDetailActivity(this, favBean.getEntity_id());
        }
    }
}
