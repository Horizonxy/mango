package com.mango.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.Constants;
import com.mango.R;
import com.mango.di.component.DaggerFoundFragmentComponent;
import com.mango.di.module.FoundFragmentModule;
import com.mango.model.bean.TrendBean;
import com.mango.presenter.FoundPresenter;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.FoundListener;
import com.mango.ui.widget.MangoPtrFrameLayout;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FoundFragment extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener,FoundListener {

    ImageView ivSearch;
    TextView tvSearch;
    TextView tvAddTend;
    View vSearch;
    MangoPtrFrameLayout refreshLayout;
    ListView listView;
    int pageNo = 1;
    boolean hasNext = true;
    List<TrendBean> datas = new ArrayList<TrendBean>();
    @Inject
    QuickAdapter adapter;
    @Inject
    FoundPresenter presenter;
    TextView tvTitle;

    public FoundFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerFoundFragmentComponent.builder().foundFragmentModule(new FoundFragmentModule(this, datas)).build().inject(this);
    }

    @Override
    void findView(View root){
        ivSearch = (ImageView) root.findViewById(R.id.iv_tab_search);
        tvSearch = (TextView) root.findViewById(R.id.tv_search);
        vSearch = root.findViewById(R.id.layout_search);
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        tvAddTend = (TextView) root.findViewById(R.id.tv_right);
        tvAddTend.setOnClickListener(this);
        tvTitle = (TextView) root.findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.found));
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
                loadData();
            }
        });

        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh(true);
            }
        }, 400);

//        List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
//        if(!indentityList.contains(Constants.UserIndentity.TUTOR) && !indentityList.contains(Constants.UserIndentity.COMMUNITY)){
//            tvAddTend.setVisibility(View.INVISIBLE);
//        } else {
//            tvAddTend.setVisibility(View.VISIBLE);
//        }
    }

    private void loadData() {
        if(hasNext) {
            presenter.getTrendList();
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TrendBean item = (TrendBean) parent.getAdapter().getItem(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                ActivityBuilder.startPublishDynamicsActivity(getActivity());
                break;
        }
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
            refreshLayout.refreshComplete();
        }

        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
        if(hasNext = (trendList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
        } else {
            refreshLayout.setNoMoreData();
        }

        datas.addAll(trendList);

        adapter.notifyDataSetChanged();
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
        if(pageNo == 1){
            refreshLayout.refreshComplete();
        }
        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
    }
}
