package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.data.MessageModel;
import cn.com.mangopi.android.presenter.MessageListReplyProjectTeamPresenter;
import cn.com.mangopi.android.presenter.MessagePresenter;
import cn.com.mangopi.android.ui.adapter.MessageListAdapter;
import cn.com.mangopi.android.ui.viewlistener.MessageListener;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.BusEvent;
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
    MessageListReplyProjectTeamPresenter replyJoinTeamPresenter;
    MessageBean clickMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);

        emptyHelper = new EmptyHelper(this, findViewById(R.id.layout_empty), null);
        emptyHelper.setImageRes(R.drawable.page_icon_02);
        emptyHelper.setMessage(R.string.page_no_message);
        messagePresenter = new MessagePresenter(new MessageModel(), this);
        initView();
        replyJoinTeamPresenter = new MessageListReplyProjectTeamPresenter(this);
        Bus.getDefault().register(this);
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

        if(messageList != null) {
            datas.addAll(messageList);
        }

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
        if(replyJoinTeamPresenter != null){
            replyJoinTeamPresenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageBean messageBean = (MessageBean) parent.getAdapter().getItem(position);

        if(Constants.EntityType.WORKS.getTypeId() == messageBean.getEntity_type()){
            DialogUtil.createProjectJoinMsgDialog(this, messageBean.getTitle(), messageBean.getResult(), new DialogUtil.OnProjectJoinMsgListener() {
                @Override
                public void onAgree() {
                    replyJoinTeamPresenter.replyJoinTeam(messageBean.getId(), 0, null);
                }

                @Override
                public void onRefuse() {
                    clickMessage = messageBean;
                    ActivityBuilder.startInputMessageActivity(MessageListActivity.this, "拒绝原因", "确定", "reject_reason", 200, "");
                }
            });
        } else {
            StringBuilder showContent = new StringBuilder();
            if (!TextUtils.isEmpty(messageBean.getTitle())) {
                showContent.append(messageBean.getTitle());
            }
            if (!TextUtils.isEmpty(messageBean.getResult())) {
                showContent.append("\n\n").append(messageBean.getResult());
            }
            if (!TextUtils.isEmpty(messageBean.getRemark())) {
                showContent.append(",").append(messageBean.getRemark());
            }
            DialogUtil.createAlertDialog(this, showContent.toString(), "确定");
//        ActivityBuilder.startContentDetailActivity(this, messageBean.getTitle(), messageBean.getResult()+"<br/>"+messageBean.getRemark());
        }

        if (messageBean.getState() == null || (messageBean.getState().intValue() != 1 && messageBean.getState().intValue() != -2)) {//不是已阅、已回复
            messagePresenter.readMessage(messageBean.getId());
            messageBean.setState(1);
            messageBean.setState_label("已阅");
            view.findViewById(R.id.tv_num).setVisibility(View.GONE);
        }

        boolean allRead = true;
        for (int i = 0; i < datas.size(); i++){
            if(datas.get(i).getState() != 1 && datas.get(i).getState() != -2){//不是已阅、已回复
                allRead = false;
                break;
            }
        }
        if(allRead){
            BusEvent.HasMessageEvent event = new BusEvent.HasMessageEvent();
            event.setHasMessage(false);
            Bus.getDefault().postSticky(event);
        }
    }

    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event) {
        String type = event.getType();
        if("reject_reason".equals(type)){
            replyJoinTeamPresenter.replyJoinTeam(clickMessage.getId(), 1, event.getContent());
        }
    }
}
