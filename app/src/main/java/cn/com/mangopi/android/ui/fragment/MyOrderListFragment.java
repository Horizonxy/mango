package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerMyOrderListFragmentComponent;
import cn.com.mangopi.android.di.module.MyOrderListFragmentModule;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.OrderListListener;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.EmptyHelper;

public class MyOrderListFragment extends BaseFragment implements AdapterView.OnItemClickListener, OrderListListener, EmptyHelper.OnRefreshListener, MyOrderListFragmentModule.OnOrderStateListener {

    PullToRefreshListView listView;

    int pageNo = 1;
    List datas = new ArrayList();
    @Inject
    QuickAdapter adapter;
    @Inject
    OrderPresenter presenter;
    boolean hasNext = true;
    int relation;
    EmptyHelper emptyHelper;

    public static MyOrderListFragment newInstance(int relation) {
        MyOrderListFragment fragment = new MyOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("relation", relation);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relation = getArguments().getInt("relation");

        DaggerMyOrderListFragmentComponent.builder().myOrderListFragmentModule(new MyOrderListFragmentModule(this, datas, relation, this)).build().inject(this);
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), this);
        emptyHelper.showRefreshButton(false);
        emptyHelper.showMessage(false);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.getRefreshableView().setDividerHeight((int) getResources().getDimension(R.dimen.dp_10));
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
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    private void loadData() {
        if(hasNext) {
            presenter.getOrderList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderBean item = (OrderBean) parent.getAdapter().getItem(position);
        ActivityBuilder.startOrderDetailActivity(getActivity(), item.getId());
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
        return getContext();
    }

    @Override
    public void onOrderListSuccess(List<OrderBean> orderList) {
        if(pageNo == 1){
            datas.clear();
        }

        listView.onRefreshComplete();
        if(hasNext = (orderList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        datas.addAll(orderList);

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
    public int getRelation() {
        return relation;
    }

    @Override
    public void onCancelSuccess(OrderBean order) {
        order.setState(-1);
        order.setState_label("订单已取消");

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        hasNext = true;
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onCancel(OrderBean order) {
        presenter.cancelOrder(order);
    }
}
