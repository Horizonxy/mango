package cn.com.mangopi.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.RecommendCourseAdapter;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.EmptyHelper;

public class CouponListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    PullToRefreshListView listView;
    EmptyHelper emptyHelper;
    int pageNo = 1;
    boolean hasNext = true;

    public CouponListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//        listView.setAdapter(adapter = new RecommendCourseAdapter(getContext(), R.layout.listview_item_recommend_teacher_class, datas));
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

        }
    }

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public static Fragment newInstance(String tabTitle) {
        CouponListFragment fragment = new CouponListFragment();
        return  fragment;
    }
}
