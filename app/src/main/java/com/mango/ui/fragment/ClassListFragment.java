package com.mango.ui.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.R;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    PtrClassicFrameLayout refreshLayout;
    ListView listView;

    int pageNo = 1;
    List datas = new ArrayList();
    QuickAdapter adapter;

    public ClassListFragment() {
    }


    @Override
    void findView(View root) {
        refreshLayout = (PtrClassicFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter = new QuickAdapter(getActivity(), R.layout.listview_item_recommend_teacher_class, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, Object item) {

            }
        });
        listView.setOnItemClickListener(this);
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                loadData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                pageNo++;
                loadData();
            }
        });
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh(true);
            }
        }, 400);
    }

    private void loadData() {
        for (int i = 0; i < 10; i++){
            datas.add("jxm: " + i);
        }

        adapter.notifyDataSetChanged();

        if(pageNo == 1){
            refreshLayout.refreshComplete();
        }
        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
    }

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
