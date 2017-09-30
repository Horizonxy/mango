package cn.com.mangopi.android.ui.viewlistener;

import cn.com.mangopi.android.model.bean.UploadBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UploadViewListener extends BaseViewListener {

    void onSuccess(UploadBean upload);
    void onUploadFailure(String message);
    void beforeUpload();
    void afterUpload(boolean success);
    long getEntityId();
    int getEntityTypeId();
    MultipartBody.Part getFile();
}
