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
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.data.MessageModel;
import cn.com.mangopi.android.presenter.MessagePresenter;
import cn.com.mangopi.android.ui.adapter.MessageListAdapter;
import cn.com.mangopi.android.ui.viewlistener.MessageListener;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.util.EmptyHelper;

public class MessageListActivity extends BaseTitleBarActivity implements MessageListener, AdapterView.OnItemClickListener{

    @Bind(R.id.refresh_layout)
    MangoPtrFrameLayout refreshLayout;
    @Bind(R.id.listview)
    ListView listView;
    int pageNo = 1;
    boolean hasNext = true;
    List<MessageBean> datas = new ArrayList<MessageBean>();
    EmptyHelper emptyHelper;
    MessagePresenter messagePresenter;
    MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);

        initView();
        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.null_msg);
        emptyHelper.showMessage(false);
        emptyHelper.showRefreshButton(false);
        messagePresenter = new MessagePresenter(new MessageModel(), this);
        loadData();
    }

    private void loadData() {
        if(hasNext) {
            messagePresenter.getMessageList();
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
        }
    }

    private void initView() {
        titleBar.setTitle(R.string.message);

        listView.setAdapter(adapter = new MessageListAdapter(this, R.layout.listview_item_messagelist, datas));
        listView.setOnItemClickListener(this);
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
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onSuccess(List<MessageBean> messageList) {
        if(pageNo == 1){
            datas.clear();
            refreshLayout.refreshComplete();
        }

        refreshLayout.setLoadMoreEnable(true);
        refreshLayout.loadMoreComplete(true);
        if(hasNext = (messageList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
        } else {
            refreshLayout.setNoMoreData();
        }

        datas.addAll(messageList);

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(refreshLayout);
        } else {
            emptyHelper.hideEmptyView(refreshLayout);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHasMessage(boolean hasMessage) {}

    @Override
    protected void onDestroy() {
        if(messagePresenter != null){
            messagePresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageBean messageBean = (MessageBean) parent.getAdapter().getItem(position);
    }
}
