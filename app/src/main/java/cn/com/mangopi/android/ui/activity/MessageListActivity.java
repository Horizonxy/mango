package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.DialogUtil;
import cn.com.mangopi.android.util.EmptyHelper;

public class MessageListActivity extends BaseTitleBarActivity implements MessageListener, AdapterView.OnItemClickListener{

    @Bind(R.id.listview)
    PullToRefreshListView listView;
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

        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_02);
        emptyHelper.setMessage(R.string.page_no_message);
        messagePresenter = new MessagePresenter(new MessageModel(), this);
        initView();
    }

    private void loadData() {
        if(hasNext) {
            messagePresenter.getMessageList();
        }
    }

    private void initView() {
        titleBar.setTitle(R.string.message);

        listView.setAdapter(adapter = new MessageListAdapter(this, R.layout.listview_item_messagelist, datas));
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

    @Override
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            listView.onRefreshComplete();

            if(datas == null || datas.size() == 0){
                emptyHelper.showEmptyView(listView);
            } else {
                emptyHelper.hideEmptyView(listView);
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
            listView.onRefreshComplete();
        }

        if(hasNext = (messageList.size() >= Constants.PAGE_SIZE)){
            pageNo++;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
        } else {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }

        datas.addAll(messageList);

        if(datas == null || datas.size() == 0){
            emptyHelper.showEmptyView(listView);
        } else {
            emptyHelper.hideEmptyView(listView);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHasMessage(boolean hasMessage) {}

    @Override
    public void readMessageSuccess() {}

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
        StringBuilder showContent = new StringBuilder();
        if(!TextUtils.isEmpty(messageBean.getTitle())){
            showContent.append(messageBean.getTitle());
        }
        if(!TextUtils.isEmpty(messageBean.getResult())){
            showContent.append("\n\n").append(messageBean.getResult());
        }
        if(!TextUtils.isEmpty(messageBean.getRemark())){
            showContent.append("\n\n").append(messageBean.getRemark());
        }
        DialogUtil.createAlertDialog(this, showContent.toString(), "确定");
//        ActivityBuilder.startContentDetailActivity(this, messageBean.getTitle(), messageBean.getResult()+"<br/>"+messageBean.getRemark());
        messagePresenter.readMessage(messageBean.getId());
        messageBean.setState(1);
        messageBean.setState_label("已阅");
        view.findViewById(R.id.tv_num).setVisibility(View.GONE);
    }
}
