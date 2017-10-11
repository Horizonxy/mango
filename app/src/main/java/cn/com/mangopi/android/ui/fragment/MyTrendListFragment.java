package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
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
import cn.com.mangopi.android.model.bean.ReplyTrendBean;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.model.data.PraiseModel;
import cn.com.mangopi.android.model.data.TrendModel;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.presenter.FoundPresenter;
import cn.com.mangopi.android.ui.adapter.TrendListAdapter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.EmptyHelper;


public class MyTrendListFragment extends BaseFragment implements AdapterView.OnItemClickListener,FoundListener {

    PullToRefreshListView listView;
    public static final int MY_SEND = 1;
    public static final int MY_REPLY = 2;

    int pageNo = 1;
    List datas = new ArrayList();
    QuickAdapter adapter;
    FoundPresenter presenter;
    FavPresenter favPresenter;
    boolean hasNext = true;
    int type;
    EmptyHelper emptyHelper;
    TrendBean favTrend;

    public MyTrendListFragment() {
    }

    public static MyTrendListFragment newInstance(int type) {
        MyTrendListFragment fragment = new MyTrendListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_04);
        emptyHelper.setMessage(R.string.page_no_trend);

        presenter = new FoundPresenter(new PraiseModel(), new TrendModel(), this);
        favPresenter = new FavPresenter(new FavModel(), this);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter = new TrendListAdapter(getActivity(), R.layout.listview_item_found, datas, this));
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
            presenter.getTrendList();
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        if(favPresenter != null){
            favPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(getContext(), message);
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onSuccess(List<TrendBean> trendList) {
        if(pageNo == 1){
            datas.clear();
        }

        listView.onRefreshComplete();
        if(hasNext = (trendList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        if(trendList != null) {
            datas.addAll(trendList);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void praise(TrendBean trend) {
        presenter.praise(trend);
    }

    @Override
    public void notifyData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure() {
        listView.onRefreshComplete();
    }

    @Override
    public void delOrAddFav(TrendBean trend) {
        this.favTrend = trend;
        if(favTrend.is_favor()){
            favPresenter.delFav();
        } else {
            favPresenter.addFav();
        }
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("page_no",getPageNo());
        map.put("page_size", Constants.PAGE_SIZE);
        if(type == MY_SEND){
            map.put("is_my_send", 1);
        } else if(type == MY_REPLY){
            map.put("is_my_reply", 1);
        }
        return map;
    }

    @Override
    public void comment(TrendBean trend) {

    }

    @Override
    public Map<String, Object> replyTrendMap() {
        return null;
    }

    @Override
    public void onReplyTrendSuccess(ReplyTrendBean replyTrendBean) {

    }
}
