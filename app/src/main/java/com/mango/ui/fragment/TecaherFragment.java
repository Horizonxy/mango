package com.mango.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.R;
import com.mango.di.Type;
import com.mango.di.component.DaggerTeacherFragmentComponent;
import com.mango.di.module.TeacherFragmentModule;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.widget.GridView;
import com.mango.util.ActivityBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TecaherFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    PtrClassicFrameLayout refreshLayout;
    ListView listView;
    int pageNo = 1;
    List listDatas = new ArrayList();
    @Inject
    @Type("list")
    QuickAdapter listAdapter;
    List gridDatas = new ArrayList();
    GridView gvCategory;
    @Inject
    @Type("grid")
    QuickAdapter gridAdapter;

    public TecaherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTeacherFragmentComponent.builder().teacherFragmentModule(new TeacherFragmentModule(getActivity(), listDatas, gridDatas)).build().inject(this);
    }

    @Override
    void findView(View root) {
        refreshLayout = (PtrClassicFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        root.findViewById(R.id.tv_left).setOnClickListener(this);
        root.findViewById(R.id.tv_right).setOnClickListener(this);
    }

    @Override
    void initView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_header_teacher, null, false);
        listView.addHeaderView(headerView);
        gvCategory = (GridView) headerView.findViewById(R.id.gv_category);
        gvCategory.setAdapter(gridAdapter);
        loadCategory();

        listView.setAdapter(listAdapter);
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

    private void loadCategory(){
        for (int i = 0; i < 8; i++){
            gridDatas.add(""+i);
        }
        gridAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        for (int i = 0; i < 10; i++){
            listDatas.add("jxm: " + i);
        }

        listAdapter.notifyDataSetChanged();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                ActivityBuilder.startTeacherClassCategoryActivity(getActivity());
                break;
            case R.id.tv_right:
                ActivityBuilder.startMyClassesActivity(getActivity());
                break;

        }
    }
}
