package cn.com.mangopi.android.ui.viewlistener;

public interface WorkProjectCommentListener extends BaseViewListener {
    long getActorId();
    int getScore();
    String getComment();
    void onCommentSuccess();
}
