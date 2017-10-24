package cn.com.mangopi.android.ui.fragment;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.orhanobut.logger.Logger;

import java.io.File;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MangoUtils;
import cn.com.mangopi.android.util.SimpleAnimatorListener;
import cn.com.mangopi.android.util.SmallPicInfo;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureDetailFragment extends BaseFragment {

    RelativeLayout rlRoot;
    ImageView ivDetail;
    ProgressBar progressLoad;

    SmallPicInfo smallPicInfo;
    PhotoViewAttacher attacher;

    private final int DURATION = 250;
    private int screenWidth, screenHeight;
    DisplayImageOptions options;
    public Bitmap bitmap;

    public static PictureDetailFragment newInstance(SmallPicInfo smallPicInfo){
        PictureDetailFragment fragment = new PictureDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("small_pic_info", smallPicInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smallPicInfo = (SmallPicInfo) getArguments().getSerializable("small_pic_info");
        this.options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    void findView(View root) {
        rlRoot = (RelativeLayout) root.findViewById(R.id.rl_root);
        ivDetail = (ImageView) root.findViewById(R.id.image_detail);
        progressLoad = (ProgressBar) root.findViewById(R.id.progress_load);
    }

    @Override
    void initView() {
        screenWidth = DisplayUtils.screenWidth(getContext());
        screenHeight = DisplayUtils.screenHeight(getContext());
        attacher = new PhotoViewAttacher(ivDetail);

        DiskCache diskCache = Application.application.getImageLoader().getDiskCache();
        File file = diskCache.get(smallPicInfo.url);
        if (file != null && file.exists()) {
            if (bitmap != null && !bitmap.isRecycled()) {
                loadOnCache(bitmap);
            } else {
                Application.application.getImageLoader().loadImage(MangoUtils.getCalculateScreenWidthSizeUrl(smallPicInfo.url), options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        loadOnCache(loadedImage);
                    }
                });
            }
        } else {
            loadOnNetwork();
        }

        attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                getActivity().onBackPressed();
            }
        });
    }

    private void loadOnCache(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scale = screenWidth * 1.0f / w;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivDetail.getLayoutParams();
        lp.height = (int) (scale * h);
        lp.width = screenWidth;
        ivDetail.setLayoutParams(lp);
        ivDetail.setImageBitmap(bitmap);
        attacher.update();

        float scaleX = smallPicInfo.width * 1.0f / lp.width;
        float scaleY = smallPicInfo.height * 1.0f / lp.height;

        int deltaX = smallPicInfo.left - (screenWidth - lp.width) / 2;
        int deltaY = smallPicInfo.top - (screenHeight - lp.height) / 2;

        ivDetail.setPivotX(0);
        ivDetail.setPivotY(0);
        ivDetail.setScaleX(scaleX);
        ivDetail.setScaleY(scaleY);

        ivDetail.setTranslationX(deltaX);
        ivDetail.setTranslationY(deltaY);

        ivDetail.animate()
                .scaleX(1f).scaleY(1f)
                .translationX(0).translationY(0)
                .setDuration(DURATION).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setImageViewMatch();
            }
        });

    }

    private void loadOnNetwork(){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivDetail.getLayoutParams();
        lp.width = smallPicInfo.width;
        lp.height = smallPicInfo.height;
        ivDetail.setLayoutParams(lp);

        if(bitmap != null && !bitmap.isRecycled()) {
            ivDetail.setImageBitmap(bitmap);
        }

        int smallDeltaX = smallPicInfo.left - (screenWidth - smallPicInfo.width) / 2;
        int smallDeltaY = smallPicInfo.top - (screenHeight - smallPicInfo.height + DisplayUtils.getStatusBarHeight(getContext())) / 2;

        ivDetail.setPivotX(0);
        ivDetail.setPivotY(0);
        ivDetail.setTranslationX(smallDeltaX);
        ivDetail.setTranslationY(smallDeltaY);

        ivDetail.animate().translationX(0).translationY(0).setDuration(DURATION).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressLoad.setVisibility(View.VISIBLE);

                Application.application.getImageLoader().displayImage(MangoUtils.getCalculateScreenWidthSizeUrl(smallPicInfo.url), ivDetail, options, new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        progressLoad.setVisibility(View.GONE);
                        attacher.update();

                        float scaleX = screenWidth * 1f / smallPicInfo.width;
                        float scaleY = screenHeight * 1f / smallPicInfo.height;
                        float scale = Math.min(scaleX, scaleY);

                        ivDetail.setPivotX(smallPicInfo.width / 2);
                        ivDetail.setPivotY(smallPicInfo.height / 2);

                        ivDetail.animate().scaleX(scale).scaleY(scale).setDuration(DURATION).setListener(new SimpleAnimatorListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ivDetail.setScaleX(1f);
                                ivDetail.setScaleY(1f);
                                setImageViewMatch();
                            }
                        });
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        progressLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        progressLoad.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void setImageViewMatch(){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivDetail.getLayoutParams();
        lp.height = screenHeight;
        lp.width = screenWidth;
        ivDetail.setLayoutParams(lp);
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_picture_detail;
    }

    public PhotoViewAttacher getAttacher() {
        return attacher;
    }

    public ImageView getIvDetail() {
        return ivDetail;
    }
}
