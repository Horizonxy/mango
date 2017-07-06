package com.mango.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.R;
import com.mango.di.component.DaggerMyClassesFragmentComponent;
import com.mango.di.module.MyClassesFragmentModule;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MyClassesFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    @Bind(R.id.refresh_layout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.listview)
    ListView listView;

    int pageNo = 1;
    List datas = new ArrayList();
    @Inject
    QuickAdapter adapter;

    public MyClassesFragment() {
    }
    public static MyClassesFragment newInstance(String param1, String param2) {
        MyClassesFragment fragment = new MyClassesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerMyClassesFragmentComponent.builder().myClassesFragmentModule(new MyClassesFragmentModule(getActivity(), datas)).build().inject(this);
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

    @Override
    int getLayoutId() {
        return R.layout.layout_pull_listview;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) parent.getAdapter().getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
    }
}
