package cn.com.mangopi.android.ui.viewlistener;

import java.util.Map;

import cn.com.mangopi.android.model.bean.ReplyTrendBean;
import cn.com.mangopi.android.model.bean.TrendDetailBean;

public interface TrendCommentsListener extends BaseViewListener {
    void onTrendSuccess(TrendDetailBean trendDetail);
    long getId();

    Map<String, Object> replyCommentMap();
    void onReplyCommentSuccess(ReplyTrendBean replyTrendBean);
}
