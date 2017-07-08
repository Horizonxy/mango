package com.mango.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.R;
import com.mango.di.component.DaggerFoundFragmentComponent;
import com.mango.di.module.FoundFragmentModule;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.util.ActivityBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FoundFragment extends BaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener {

    ImageView ivSearch;
    TextView tvSearch;
    View vSearch;
    PtrClassicFrameLayout refreshLayout;
    ListView listView;
    int pageNo = 1;
    List datas = new ArrayList();
    @Inject
    QuickAdapter adapter;

    public FoundFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerFoundFragmentComponent.builder().foundFragmentModule(new FoundFragmentModule(getActivity(), datas)).build().inject(this);
    }

    @Override
    void findView(View root){
        ivSearch = (ImageView) root.findViewById(R.id.iv_tab_search);
        tvSearch = (TextView) root.findViewById(R.id.tv_search);
        vSearch = root.findViewById(R.id.layout_search);
        refreshLayout = (PtrClassicFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        root.findViewById(R.id.tv_right).setOnClickListener(this);
    }

    @Override
    void initView() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.dp_10));
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
        return R.layout.fragment_found;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) parent.getAdapter().getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                ActivityBuilder.startPublishDynamicsActivity(getActivity());
                break;
        }
    }
}
