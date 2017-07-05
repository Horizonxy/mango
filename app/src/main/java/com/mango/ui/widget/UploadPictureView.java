package com.mango.ui.widget;

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
import com.mango.Application;
import com.mango.R;
import com.mango.model.bean.UploadImageBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class UploadPictureView extends FrameLayout implements View.OnClickListener {

    ImageView ivPicture;
    ProgressBar progress;
    ImageView ivRetry;
    ImageView ivDelete;
    View layoutAdd;
    UploadImageBean imageBean;
    DisplayImageOptions options;

    OnUploadPictureListener onUploadPictureListener;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_retry:
                break;
            case R.id.iv_delete:
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
                Application.application.getImageLoader().displayImage(imageBean.getLocalPath(), ivPicture, options);
                ivDelete.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                ivRetry.setVisibility(View.GONE);
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

    public interface OnUploadPictureListener {
        void onAddPicture();
    }

}
