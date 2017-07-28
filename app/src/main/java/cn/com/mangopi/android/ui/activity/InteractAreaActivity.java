package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerInteractAreaActivityComponent;
import cn.com.mangopi.android.di.module.InteractAreaActivityModule;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.util.AppUtils;

public class InteractAreaActivity extends BaseTitleBarActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.refresh_layout)
    MangoPtrFrameLayout refreshLayout;
    @Bind(R.id.listview)
    ListView listView;
    int pageNo = 1;
    List datas = new ArrayList();
    @Inject
    QuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_area);
        DaggerInteractAreaActivityComponent.builder().interactAreaActivityModule(new InteractAreaActivityModule(this, datas)).build().inject(this);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.interact_area);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.dp_10));
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
        for (int i = 0; i < 2; i++){
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
        AppUtils.showToast(this, item);
    }
}