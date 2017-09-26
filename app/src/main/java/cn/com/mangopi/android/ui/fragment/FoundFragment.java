package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerFoundFragmentComponent;
import cn.com.mangopi.android.di.module.FoundFragmentModule;
import cn.com.mangopi.android.model.bean.ReplyTrendBean;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.presenter.FoundPresenter;
import cn.com.mangopi.android.presenter.TrendCommentsPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.FavListener;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.EmptyHelper;
import cn.com.mangopi.android.util.MangoUtils;

public class FoundFragment extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener,FoundListener, FavListener {

    public static final int MY_SEND = 1;
    public static final int MY_REPLY = 2;

    ImageView ivSearch;
    TextView tvSearch;
    TextView tvAddTend;
    View vSearch;
    PullToRefreshListView listView;
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
    EmptyHelper emptyHelper;
    TrendCommentsPresenter trendCommentsPresenter;
    int type;

    public FoundFragment() {
    }

    public static FoundFragment newInstance(int type) {
        FoundFragment fragment = new FoundFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            type = getArguments().getInt("type");
        }
        Bus.getDefault().register(this);
        DaggerFoundFragmentComponent.builder().foundFragmentModule(new FoundFragmentModule(this, datas)).build().inject(this);
        trendCommentsPresenter = new TrendCommentsPresenter(this);
    }

    @Override
    void findView(View root){
        ivSearch = (ImageView) root.findViewById(R.id.iv_tab_search);
        tvSearch = (TextView) root.findViewById(R.id.tv_search);
        vSearch = root.findViewById(R.id.layout_search);
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        tvAddTend = (TextView) root.findViewById(R.id.tv_right);
        tvAddTend.setOnClickListener(this);
        tvTitle = (TextView) root.findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.found));
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_04);
        emptyHelper.setMessage(R.string.page_no_trend);
        if(type == MY_SEND || type == MY_REPLY){
            root.findViewById(R.id.layout_tab).setVisibility(View.GONE);
        }
    }

    @Override
    void initView() {
        listView.setAdapter(adapter);
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
                loadData();
            }
        });
        listView.setRefreshing(true);

        List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
        if(!indentityList.contains(Constants.UserIndentity.TUTOR) && !indentityList.contains(Constants.UserIndentity.COMMUNITY)){
            tvAddTend.setVisibility(View.INVISIBLE);
        } else {
            tvAddTend.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        if(hasNext) {
            presenter.getTrendList();
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TrendBean item = (TrendBean) parent.getAdapter().getItem(position);
        ActivityBuilder.startTrendCommentsActivity(getActivity(), item.getId(), item.getFawordTrend());
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
        }

        listView.onRefreshComplete();
        if(hasNext = (trendList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        datas.addAll(trendList);

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
        commentTrendContent = "";
        commentTrend = null;
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

    TrendBean commentTrend;
    String commentTrendContent;
    @Override
    public void comment(TrendBean trend) {
        commentTrend = trend;
        ActivityBuilder.startInputMessageActivity(getActivity(), "评论动态", "确定", "comment_trend", 100, null);
    }

    @Override
    public Map<String, Object> replyTrendMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trend_id", commentTrend.getId());
        map.put("content", commentTrendContent);
        return map;
    }

    @Override
    public void onReplyTrendSuccess(ReplyTrendBean replyTrendBean) {
        commentTrend.setComment_count(commentTrend.getComment_count() + 1);
        adapter.notifyDataSetChanged();
        ActivityBuilder.startTrendCommentsActivity(getActivity(), commentTrend.getId(), commentTrend.getFawordTrend());
        commentTrendContent = "";
        commentTrend = null;
    }

    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event) {
        String type = event.getType();
        if ("comment_trend".equals(type)) {
            commentTrendContent = event.getContent();
            trendCommentsPresenter.replyTrend();
        }
    }

    @BusReceiver
    public void onRefreshTrendListEvent(BusEvent.RefreshTrendListEvent event){
        listView.getRefreshableView().smoothScrollToPosition(0);
        listView.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        Bus.getDefault().unregister(this);
        if(presenter != null) {
            presenter.onDestroy();
        }
        if(favPresenter != null){
            favPresenter.onDestroy();
        }
        super.onDestroy();
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
