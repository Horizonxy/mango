package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;

import cn.com.mangopi.android.model.bean.MessageBean;

public interface MessageListener extends BaseViewListener {

    int getPageNo();
    void onSuccess(List<MessageBean> messageList);
    void onHasMessage(boolean hasMessage);
    void readMessageSuccess();
}
