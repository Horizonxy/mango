package com.mango.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.R;
import com.mango.di.component.DaggerTeacherFragmentComponent;
import com.mango.di.module.TeacherFragmentModule;
import com.mango.ui.adapter.quickadapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class TecaherFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.refresh_layout)
    PtrClassicFrameLayout refreshLayout;
    @Bind(R.id.listview)
    ListView listView;
    int pageNo = 1;
    List datas = new ArrayList();
    @Inject
    QuickAdapter adapter;

    public TecaherFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTeacherFragmentComponent.builder().teacherFragmentModule(new TeacherFragmentModule(getActivity(), datas)).build().inject(this);
    }

    @Override
    void initView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_header_teacher, null, false);
        listView.addHeaderView(headerView);

        listView.setAdapter(adapter);
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
        return R.layout.fragment_tecaher;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
