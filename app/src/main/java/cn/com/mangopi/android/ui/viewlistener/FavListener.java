package cn.com.mangopi.android.ui.viewlistener;

public interface FavListener extends BaseViewListener{
    long getEntityId();
    int getEntityTypeId();
    void onSuccess(boolean success);
}
