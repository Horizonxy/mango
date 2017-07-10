package com.mango.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrUIHandler;
import com.chanven.lib.cptr.indicator.PtrIndicator;
import com.mango.R;

public class MangoPtrUIHandler extends FrameLayout implements PtrUIHandler {

    private int rotateAniTime = 150;
    private RotateAnimation flipAnimation;
    private RotateAnimation reverseFlipAnimation;
    private ImageView ivRotate;
    private GifMovieView gifProgress;
    private TextView tvRefresh;

    public MangoPtrUIHandler(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_ptr_refresh_header, this);
        ivRotate = (ImageView) header.findViewById(R.id.iv_rotate);
        gifProgress = (GifMovieView) header.findViewById(R.id.gif_progress);
        tvRefresh = (TextView) header.findViewById(R.id.tv_refresh);
        gifProgress.setMovieResource(R.raw.loadinggif);

        buildAnimation();

        resetView();
    }

    private void resetView() {
        hideRotateView();
        gifProgress.setVisibility(INVISIBLE);
    }

    private void hideRotateView() {
        ivRotate.clearAnimation();
        ivRotate.setVisibility(INVISIBLE);
    }

    private void buildAnimation() {
        flipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        flipAnimation.setInterpolator(new LinearInterpolator());
        flipAnimation.setDuration(rotateAniTime);
        flipAnimation.setFillAfter(true);

        reverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseFlipAnimation.setInterpolator(new LinearInterpolator());
        reverseFlipAnimation.setDuration(rotateAniTime);
        reverseFlipAnimation.setFillAfter(true);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        ivRotate.setVisibility(VISIBLE);
        tvRefresh.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            tvRefresh.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down_to_refresh));
        } else {
            tvRefresh.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down));
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        hideRotateView();
        gifProgress.setVisibility(VISIBLE);
        tvRefresh.setVisibility(VISIBLE);
        tvRefresh.setText(com.chanven.lib.cptr.R.string.cube_ptr_refreshing);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        hideRotateView();
        gifProgress.setVisibility(INVISIBLE);

        tvRefresh.setVisibility(VISIBLE);
        tvRefresh.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_refresh_complete));
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
                if (ivRotate != null) {
                    ivRotate.clearAnimation();
                    ivRotate.startAnimation(reverseFlipAnimation);
                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
                if (ivRotate != null) {
                    ivRotate.clearAnimation();
                    ivRotate.startAnimation(flipAnimation);
                }
            }
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        tvRefresh.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            tvRefresh.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down_to_refresh));
        } else {
            tvRefresh.setText(getResources().getString(com.chanven.lib.cptr.R.string.cube_ptr_pull_down));
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            tvRefresh.setVisibility(VISIBLE);
            tvRefresh.setText(com.chanven.lib.cptr.R.string.cube_ptr_release_to_refresh);
        }
    }
}
