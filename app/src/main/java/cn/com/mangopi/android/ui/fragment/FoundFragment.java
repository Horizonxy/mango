package cn.com.mangopi.android.ui.fragment;

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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerFoundFragmentComponent;
import cn.com.mangopi.android.di.module.FoundFragmentModule;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.presenter.FoundPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.FavListener;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

public class FoundFragment extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener,FoundListener, FavListener {

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
    @Inject
    FavPresenter favPresenter;
    TrendBean favTrend;

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
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
        if(favPresenter != null){
            favPresenter.onDestroy();
        }
    }

    @Override
    public long getEntityId() {
        if(favTrend != null){
            return favTrend.getId();
        }
        return 0;
    }

    @Override
    public int getEntityTypeId() {
        return Constants.EntityType.TREND.getTypeId();
    }

    @Override
    public void onSuccess(boolean success) {
        favTrend.setIs_favor(success);
        adapter.notifyDataSetChanged();
    }
}
