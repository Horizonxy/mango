package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.presenter.WalletTransListPresenter;
import cn.com.mangopi.android.ui.adapter.MemberTransListAdapter;
import cn.com.mangopi.android.ui.viewlistener.WalletTransListListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.EmptyHelper;

public class MemberTransListActivity extends BaseTitleBarActivity implements WalletTransListListener, AdapterView.OnItemClickListener{

    @Bind(R.id.listview)
    PullToRefreshListView listView;
    int pageNo = 1;
    boolean hasNext = true;
    List<TransListBean> datas = new ArrayList<TransListBean>();
    EmptyHelper emptyHelper;
    WalletTransListPresenter transListPresenter;
    MemberTransListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);

        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_06);
        emptyHelper.setMessage(R.string.page_no_data);
        transListPresenter = new WalletTransListPresenter(this);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.member_trans_list);

        listView.setAdapter(adapter = new MemberTransListAdapter(this, R.layout.listview_item_member_trans_list, datas));
        listView.setOnItemClickListener(this);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<android.widget.ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<android.widget.ListView> refreshView) {
                pageNo = 1;
                hasNext = true;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<android.widget.ListView> refreshView) {
                pageNo++;
                loadData();
            }
        });
        listView.setRefreshing(true);
    }

    private void loadData() {
        if(hasNext) {
            transListPresenter.walletTransList();
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
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onTransListSuccess(List<TransListBean> transList) {
        if(pageNo == 1){
            datas.clear();
            listView.onRefreshComplete();
        }

        if(hasNext = (transList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        if(transList != null) {
            datas.addAll(transList);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TransListBean trans = (TransListBean) parent.getAdapter().getItem(position);
        if(trans != null){
            ActivityBuilder.startTransDetailActivity(this, trans);
        }
    }

    @Override
    protected void onDestroy() {
        if(transListPresenter != null){
            transListPresenter.onDestroy();
        }
        super.onDestroy();
    }

}
