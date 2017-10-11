package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.presenter.CouponListPresenter;
import cn.com.mangopi.android.ui.adapter.CouponListAdapter;
import cn.com.mangopi.android.ui.viewlistener.CouponListListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.EmptyHelper;

public class CouponListFragment extends BaseFragment implements AdapterView.OnItemClickListener, CouponListListener {

    PullToRefreshListView listView;
    EmptyHelper emptyHelper;
    int pageNo = 1;
    boolean hasNext = true;
    List<CouponBean> datas = new ArrayList<>();
    int state;
    CouponListAdapter adapter;
    CouponListPresenter presenter;

    public CouponListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            state = getArguments().getInt("state");
        }
        presenter = new CouponListPresenter(this);
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        emptyHelper = new EmptyHelper(getContext(), root.findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_06);
        emptyHelper.setMessage(R.string.page_no_data);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter = new CouponListAdapter(getActivity(), R.layout.listview_item_member_coupon_list, datas));
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
            presenter.memberCouponList();
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public static Fragment newInstance(int state) {
        CouponListFragment fragment = new CouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return  fragment;
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
        return getActivity();
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void onCouponListSuccess(List<CouponBean> couponList) {
        if(pageNo == 1){
            datas.clear();
        }

        listView.onRefreshComplete();
        if(hasNext = (couponList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        if(couponList != null) {
            datas.addAll(couponList);
        }

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
            adapter.notifyDataSetChanged();
        }
    }
}
