package cn.com.mangopi.android.ui.viewlistener;

public interface WantCountListener extends BaseViewListener {
    void onWantCountSuccess();
    long wantEntityId();
    int wantEntityType();
}
