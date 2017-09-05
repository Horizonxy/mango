package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.ViewPagerAdapter;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DisplayUtils;

public class GuideActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    List<View> pages = new ArrayList<>();
    @Bind(R.id.layout_indicator)
    LinearLayout layoutIndicator;
    @Bind(R.id.btn_start)
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initPages();
        initView();
    }

    private void initView() {
        viewPager.setAdapter(new ViewPagerAdapter(pages));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < layoutIndicator.getChildCount(); i++) {
                    if (i == position) {
                        ((ImageView) layoutIndicator.getChildAt(position)).setImageResource(R.drawable.shape_indicator_selected);
                    } else {
                        ((ImageView) layoutIndicator.getChildAt(i)).setImageResource(R.drawable.shape_indicator_normal);
                    }
                }
                btnStart.setVisibility(position == (pages.size() - 1) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ((ImageView) layoutIndicator.getChildAt(viewPager.getCurrentItem())).setImageResource(R.drawable.shape_indicator_selected);
    }

    private void initPages(){
        pages.clear();
        ImageView page1 = (ImageView) getLayoutInflater().inflate(R.layout.layout_guide_page_item, null, false);
        page1.setImageResource(R.drawable.guide1);
        pages.add(page1);
        ImageView page2 = (ImageView) getLayoutInflater().inflate(R.layout.layout_guide_page_item, null, false);
        page2.setImageResource(R.drawable.guide2);
        pages.add(page2);
        ImageView page3 = (ImageView) getLayoutInflater().inflate(R.layout.layout_guide_page_item, null, false);
        page3.setImageResource(R.drawable.guide3);
        pages.add(page3);
        ImageView page4 = (ImageView) getLayoutInflater().inflate(R.layout.layout_guide_page_item, null, false);
        page4.setImageResource(R.drawable.guide4);
        pages.add(page4);

        for (int i = 0; i < pages.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(DisplayUtils.dip2px(this, 2.5F), 0, DisplayUtils.dip2px(this, 2.5F), 0);
            imageView.setImageResource(R.drawable.shape_indicator_normal);
            imageView.setBackgroundResource(android.R.color.transparent);
            layoutIndicator.addView(imageView);
        }
    }

    @OnClick(R.id.btn_start)
    void clickStart(){
        ActivityBuilder.startMainActivity(this);
        ActivityBuilder.defaultTransition(this);
        finish();
    }

    @Override
    public int statusColorResId() {
        return android.R.color.transparent;
    }
}
