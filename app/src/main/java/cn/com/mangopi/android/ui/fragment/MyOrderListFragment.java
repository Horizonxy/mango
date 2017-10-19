package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import cn.com.mangopi.android.di.component.DaggerMyOrderListFragmentComponent;
import cn.com.mangopi.android.di.module.MyOrderListFragmentModule;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.OrderListListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.EmptyHelper;

public class MyOrderListFragment extends BaseFragment implements AdapterView.OnItemClickListener, OrderListListener, EmptyHelper.OnRefreshListener, MyOrderListFragmentModule.OnOrderStateListener {

    PullToRefreshListView listView;

    int pageNo = 1;
    List<OrderBean> datas = new ArrayList<OrderBean>();
    @Inject
    QuickAdapter adapter;
    @Inject
    OrderPresenter presenter;
    boolean hasNext = true;
    int relation;
    EmptyHelper emptyHelper;
    FrameLayout layoutSeePlan;
    String sctDate;
    int sctTime;
    RelativeLayout layoutMainContent;

    public static MyOrderListFragment newInstance(int relation) {
        MyOrderListFragment fragment = new MyOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_ORDER_RELATION, relation);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MyOrderListFragment newInstance(int relation, String sctDate, int sctTime) {
        MyOrderListFragment fragment = new MyOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_ORDER_RELATION, relation);
        bundle.putString(Constants.BUNDLE_ORDER_SCT_DATE, sctDate);
        bundle.putInt(Constants.BUNDLE_ORDER_SCT_TIME, sctTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            relation = getArguments().getInt(Constants.BUNDLE_ORDER_RELATION);
            sctDate = getArguments().getString(Constants.BUNDLE_ORDER_SCT_DATE);
            sctTime = getArguments().getInt(Constants.BUNDLE_ORDER_SCT_TIME);
        }
        DaggerMyOrderListFragmentComponent.builder().myOrderListFragmentModule(new MyOrderListFragmentModule(this, datas, relation, this)).build().inject(this);
        Bus.getDefault().register(this);
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), this);
        emptyHelper.setImageRes(R.drawable.page_icon_06);
        emptyHelper.setMessage(R.string.page_no_data);
        layoutSeePlan = (FrameLayout) root.findViewById(R.id.layout_see_plan);
        layoutSeePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBuilder.startOrderScheduleCalendarActivity(getActivity());
            }
        });

        if(relation == 2) {
            if(!TextUtils.isEmpty(sctDate) && sctTime != 0){
                layoutSeePlan.setVisibility(View.GONE);
            } else {
                layoutSeePlan.setVisibility(View.VISIBLE);
            }
        } else {
            layoutSeePlan.setVisibility(View.GONE);
        }

        layoutMainContent = (RelativeLayout) root.findViewById(R.id.layout_main_content);
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
                pageNo++;
                loadData();
            }
        });
        listView.setRefreshing(true);

    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    private void loadData() {
        if(hasNext) {
            presenter.getOrderList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderBean item = (OrderBean) parent.getAdapter().getItem(position);
        ActivityBuilder.startOrderDetailActivity(getActivity(), item.getId(), relation);
    }

    @Override
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            listView.onRefreshComplete();

            if(datas == null || datas.size() == 0){
                emptyHelper.showEmptyView(layoutMainContent);
            } else {
                emptyHelper.hideEmptyView(layoutMainContent);
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

        if(orderList != null) {
            datas.addAll(orderList);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(layoutMainContent);
        } else {
            emptyHelper.hideEmptyView(layoutMainContent);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lst_sessid", Application.application.getSessId());
        map.put("page_no", pageNo);
        map.put("page_size", Constants.PAGE_SIZE);
        map.put("relation", relation);
        if(!TextUtils.isEmpty(sctDate)){
            map.put("sct_date", sctDate);
        }
        if(sctTime != 0){
            map.put("sct_time", sctTime);
        }
        return map;
    }

    @Override
    public void onCancelSuccess(OrderBean order) {
        order.setState(-1);
        order.setState_label("订单已取消");

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelScheduleSuccess(OrderBean order) {
        order.setState(4);
        order.setState_label("订单已付款，待安排");

        adapter.notifyDataSetChanged();
    }

    @BusReceiver
    public void onCancelOrderEvent(BusEvent.CancelOrderEvent event){
        for (OrderBean order : datas){
            if(order.getId() == event.getId()){
                order.setState(-1);
                order.setState_label("订单已取消");
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @BusReceiver
    public void onCancelOrderSeheduleEvent(BusEvent.CancelOrderSeheduleEvent event){
        for (OrderBean order : datas){
            if(order.getId() == event.getId()){
                order.setState(4);
                order.setState_label("订单已付款，待安排");
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        hasNext = true;
        loadData();
    }

    @Override
    public void onDestroy() {
        if(presenter != null) {
            presenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCancel(OrderBean order) {
        presenter.cancelOrder(order);
    }

    @Override
    public void onCancelScdule(OrderBean order) {
        presenter.cancelSchedule(order);
    }
}
