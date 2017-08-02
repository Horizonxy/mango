package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.model.data.FavModel;
import cn.com.mangopi.android.presenter.FavPresenter;
import cn.com.mangopi.android.ui.adapter.FavListAdapter;
import cn.com.mangopi.android.ui.viewlistener.FavListListener;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.util.EmptyHelper;

public class FavListActivity extends BaseTitleBarActivity implements FavListListener, AdapterView.OnItemClickListener{

    @Bind(R.id.refresh_layout)
    MangoPtrFrameLayout refreshLayout;
    @Bind(R.id.listview)
    ListView listView;
    int pageNo = 1;
    boolean hasNext = true;
    List<FavBean> datas = new ArrayList<FavBean>();
    EmptyHelper emptyHelper;
    FavPresenter favPresenter;
    FavListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);
        
        initView();
        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.null_cs);
        emptyHelper.showMessage(false);
        emptyHelper.showRefreshButton(false);
        favPresenter = new FavPresenter(new FavModel(), this);
        loadData();
    }

    private void loadData() {
        if(hasNext) {
            favPresenter.getFavList();
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
        }
    }

    private void initView() {
        titleBar.setTitle(R.string.fav);
        listView.setAdapter(adapter = new FavListAdapter(this, R.layout.listview_item_favlist, datas));
        listView.setOnItemClickListener(this);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.dp_10));
        refreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                hasNext = true;
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
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            if (pageNo == 1) {
                refreshLayout.refreshComplete();
            }
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);

            if(datas == null || datas.size() == 0){
                emptyHelper.showEmptyView(refreshLayout);
            } else {
                emptyHelper.hideEmptyView(refreshLayout);
            }
        }
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onSuccess(List<FavBean> favList) {
        if(pageNo == 1){
            datas.clear();
            refreshLayout.refreshComplete();
        }

        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
        if(hasNext = (favList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
        } else {
            refreshLayout.setNoMoreData();
        }

        datas.addAll(favList);

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(refreshLayout);
        } else {
            emptyHelper.hideEmptyView(refreshLayout);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
