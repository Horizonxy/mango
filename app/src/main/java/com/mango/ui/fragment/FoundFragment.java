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

import butterknife.Bind;
import butterknife.OnClick;

public class FoundFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.iv_tab_search)
    ImageView ivSearch;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.layout_search)
    View vSearch;
    @Bind(R.id.refresh_layout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.listview)
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
                loadData();
            }
        });

        loadData();
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

    @OnClick(R.id.tv_right)
    void addFound(){
        ActivityBuilder.startPublishDynamicsActivity(getActivity());
    }
}
