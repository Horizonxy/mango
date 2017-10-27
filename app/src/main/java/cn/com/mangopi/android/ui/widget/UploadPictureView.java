package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.orhanobut.logger.Logger;

import java.io.File;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.UploadBean;
import cn.com.mangopi.android.model.bean.UploadImageBean;
import cn.com.mangopi.android.model.data.UploadModel;
import cn.com.mangopi.android.presenter.UploadPresenter;
import cn.com.mangopi.android.ui.viewlistener.UploadViewListener;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPictureView extends FrameLayout implements View.OnClickListener, UploadViewListener {

    ImageView ivPicture;
    ProgressBar progress;
    ImageView ivRetry;
    ImageView ivDelete;
    View layoutAdd;
    UploadImageBean imageBean;
    DisplayImageOptions options;
    MultipartBody.Part uploadImage;
    OnUploadPictureListener onUploadPictureListener;
    UploadPresenter presenter;
    int addIconResId;

    public UploadPictureView(@NonNull Context context) {
        this(context, null);
    }

    public UploadPictureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPictureView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.gridview_item_add_picture, this, true);
        layoutAdd = root.findViewById(R.id.layout_add);
        ivPicture = (ImageView) root.findViewById(R.id.iv_picture);
        progress = (ProgressBar) root.findViewById(R.id.progress);
        ivRetry = (ImageView) root.findViewById(R.id.iv_retry);
        ivDelete = (ImageView) root.findViewById(R.id.iv_delete);

        ivRetry.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        layoutAdd.setOnClickListener(this);

        options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
        presenter = new UploadPresenter(new UploadModel(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_retry:
                presenter.upload();
                break;
            case R.id.iv_delete:
                if(onUploadPictureListener != null){
                    onUploadPictureListener.onDelView(imageBean);
                }
                break;
            case R.id.layout_add:
                if(onUploadPictureListener != null){
                    onUploadPictureListener.onAddPicture();
                }
                break;
        }
    }

    public UploadImageBean getImageBean() {
        return imageBean;
    }

    public void setImageBean(UploadImageBean imageBean) {
        this.imageBean = imageBean;
        if(imageBean.getType() != UploadImageBean.ADD_BTN){
            File file = FileUtils.compressImageFromPath(getContext(), imageBean.getLocalPath());
            RequestBody requestFile = RequestBody.create(MediaType.parse(Constants.FORM_DATA), file);
            uploadImage = MultipartBody.Part.createFormData(Constants.FILE_PARAM, file.getName(), requestFile);
        }
        setImageLayout();
    }

    private void setImageLayout(){
        if(imageBean == null){
            return;
        }
        int type = imageBean.getType();
        switch (type){
            case UploadImageBean.ADD_BTN:
                layoutAdd.setVisibility(View.VISIBLE);
                ivPicture.setVisibility(View.GONE);
                ivDelete.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                ivRetry.setVisibility(View.GONE);
                break;
            case UploadImageBean.READY:
                layoutAdd.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                String path = imageBean.getLocalPath();
                String suffix = FileUtils.getMIMEType(path);
                if(FileUtils.FILE_TYPE_IMAGE.equals(suffix)) {
                    Application.application.getImageLoader().displayImage(Constants.FILE_PREFIX + path, ivPicture, options);
                } else {
                    ivPicture.setImageResource(AppUtils.getResIdBySuffix(path));
                }
                ivDelete.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                ivRetry.setVisibility(View.GONE);
                presenter.uploadWithOutLoading();
                break;
            case UploadImageBean.UPLOADED:
                layoutAdd.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                ivDelete.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                ivRetry.setVisibility(View.GONE);
                break;
            case UploadImageBean.RETRY:
                layoutAdd.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                ivDelete.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                ivRetry.setVisibility(View.VISIBLE);
                break;
            case UploadImageBean.UPLOADING:
                layoutAdd.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                ivDelete.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                ivRetry.setVisibility(View.GONE);
                break;
        }
    }

    public void setOnUploadPictureListener(OnUploadPictureListener onUploadPictureListener) {
        this.onUploadPictureListener = onUploadPictureListener;
    }

    @Override
    public void onFailure(String message) {
        imageBean.setType(UploadImageBean.RETRY);
        imageBean.setUploadBean(null);
        setImageLayout();
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onSuccess(UploadBean upload) {
        imageBean.setType(UploadImageBean.UPLOADED);
        imageBean.setUploadBean(upload);
        setImageLayout();
        uploadImage = null;
    }

    @Override
    public void onUploadFailure(String message) {
        imageBean.setType(UploadImageBean.RETRY);
        imageBean.setUploadBean(null);
        setImageLayout();
    }

    @Override
    public void beforeUpload() {
    }

    @Override
    public void afterUpload(boolean success) {
    }

    @Override
    public long getEntityId() {
        return 0;
    }

    @Override
    public int getEntityTypeId() {
        return Constants.EntityType.TREND.getTypeId();
    }

    @Override
    public MultipartBody.Part getFile() {
        return uploadImage;
    }

    public interface OnUploadPictureListener {
        void onAddPicture();
        void onDelView(UploadImageBean uploadImage);
    }

    @Override
    protected void onDetachedFromWindow() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDetachedFromWindow();
    }

    public void setAddIconResId(int addIconResId) {
        layoutAdd.setBackgroundResource(addIconResId);
    }
}
