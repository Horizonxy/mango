package cn.com.mangopi.android.ui.viewlistener;

/**
 * @author 蒋先明
 * @date 2017/7/7
 */

public interface SetNickNameListener extends BaseViewListener {

    void onSuccess();
    String getNickName();
    int getGender();
}
