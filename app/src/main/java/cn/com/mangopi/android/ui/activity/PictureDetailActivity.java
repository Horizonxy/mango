package cn.com.mangopi.android.ui.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.FragmentAdapter;
import cn.com.mangopi.android.ui.fragment.PictureDetailFragment;
import cn.com.mangopi.android.ui.widget.ViewPagerFixed;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.SimpleAnimatorListener;
import cn.com.mangopi.android.util.SmallPicInfo;
import cn.com.mangopi.android.util.SystemStatusManager;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureDetailActivity extends FragmentActivity {

    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    List<SmallPicInfo> smallPicInfos;
    List<Fragment> fragments;
    private final int DURATION = 250;
    private boolean isExit;
    @Bind(R.id.view_pager)
    ViewPagerFixed viewPager;
    @Bind(R.id.indicator)
    LinearLayout indicator;
    private int position;
    public static Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setTranslucentStatusRes(this, android.R.color.transparent);
        setContentView(R.layout.activity_picture_detail);
        ButterKnife.bind(this);

        rlRoot.setAlpha(0f);
        rlRoot.animate().alpha(1f).setDuration(DURATION);

        smallPicInfos = (List<SmallPicInfo>) getIntent().getSerializableExtra(Constants.BUNDLE_URL_LIST);
        position = getIntent().getIntExtra(Constants.BUNDLE_POSITION, -1);

        if(smallPicInfos == null || smallPicInfos.size() == 0){
            finish();
            return;
        }

        fragments = new ArrayList<>();
        indicator.removeAllViews();
        for (int i = 0; i < smallPicInfos.size(); i++){
            PictureDetailFragment fragment = PictureDetailFragment.newInstance(smallPicInfos.get(i));
            if(i == position && bmp != null && !bmp.isRecycled()){
                fragment.setBitmap(bmp);
            }
            fragments.add(fragment);

            ImageView imageView = new ImageView(this);
            imageView.setPadding(DisplayUtils.dip2px(this, 2.5F), 0, DisplayUtils.dip2px(this, 2.5F), 0);
            imageView.setImageResource(R.drawable.shape_indicator_normal);
            imageView.setBackgroundResource(android.R.color.transparent);
            indicator.addView(imageView);
        }
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        if(smallPicInfos.size() > 1) {
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < indicator.getChildCount(); i++) {
                        if (i == position) {
                            ((ImageView) indicator.getChildAt(position)).setImageResource(R.drawable.shape_indicator_selected);
                        } else {
                            ((ImageView) indicator.getChildAt(i)).setImageResource(R.drawable.shape_indicator_normal);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            if (position > 0) {
                viewPager.setCurrentItem(position);
            }
            ((ImageView) indicator.getChildAt(viewPager.getCurrentItem())).setImageResource(R.drawable.shape_indicator_selected);
            indicator.setVisibility(View.VISIBLE);
        } else {
            indicator.setVisibility(View.GONE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onBackPressed() {
        if(isExit){
            return;
        }
        isExit = true;

        rlRoot.animate().alpha(0.4f).setDuration(DURATION);

        int position =  viewPager.getCurrentItem();
        SmallPicInfo smallPicInfo = smallPicInfos.get(position);
        PhotoViewAttacher attacher = ((PictureDetailFragment)fragments.get(position)).getAttacher();
        ImageView ivDetail = ((PictureDetailFragment)fragments.get(position)).getIvDetail();
        RectF rect = attacher.getDisplayRect();
        float scaleX = smallPicInfo.width * 1f / Math.min(rect.width(), ivDetail.getWidth());
        float scaleY = smallPicInfo.height * 1f / Math.min(rect.height(), ivDetail.getHeight());

        int[] location = new int[2];
        ivDetail.getLocationOnScreen(location);
        int deltaX = (int) (smallPicInfo.left - (location[0] + (ivDetail.getWidth() - rect.width() > 0 ? scaleX * (ivDetail.getWidth() - rect.width()) / 2 : 0)));
        int deltaY = (int) (smallPicInfo.top - (location[1] + (ivDetail.getHeight() - rect.height() > 0 ? scaleY * (ivDetail.getHeight() - rect.height()) / 2 : 0)));

        ivDetail.setPivotX(0);
        ivDetail.setPivotY(0);
        ivDetail.setScaleX(1f);
        ivDetail.setScaleY(1f);
        ivDetail.setTranslationX(0);
        ivDetail.setTranslationY(0);

        ivDetail.animate().scaleX(scaleX).scaleY(scaleY).translationX(deltaX).translationY(deltaY).setDuration(DURATION).setListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                PictureDetailActivity.super.onBackPressed();
                isExit = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smallPicInfos = null;
        if(bmp != null && !bmp.isRecycled()){
            if(position > 0) {
                ((PictureDetailFragment)fragments.get(position)).setBitmap(null);
                bmp.recycle();
                bmp = null;
            }
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
