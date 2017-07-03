package com.mango.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.mango.Application;
import com.mango.R;
import com.mango.ui.adapter.ViewPagerAdapter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.widget.GridView;
import com.mango.ui.widget.ObservableScrollView;
import com.mango.ui.widget.VerticalTextview;
import com.mango.ui.widget.ViewPagerFixed;
import com.mango.util.DisplayUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    @Bind(R.id.home_banner)
    ConvenientBanner homeBanner;
    List<String> banners;
    @Bind(R.id.tv_scroll)
    VerticalTextview tvScroll;
    @Bind(R.id.home_pager)
    ViewPagerFixed homePager;
    @Bind(R.id.home_indicator)
    MagicIndicator homeIndicator;
    @Bind(R.id.sv_content)
    ObservableScrollView svContent;
    @Bind(R.id.layout_home_bar)
    RelativeLayout layoutHomeBar;

    List<View> gridViews = new ArrayList<>();

    public HomeFragment() {
    }


    @Override
    void initView() {
        banners = new ArrayList<>();
        banners.add("http://pic.58pic.com/58pic/13/61/00/61a58PICtPr_1024.jpg");
        banners.add("http://pic.58pic.com/58pic/13/60/91/73c58PICsbi_1024.jpg");
        banners.add("http://img2.imgtn.bdimg.com/it/u=2689786592,2235288056&fm=21&gp=0.jpg");
        homeBanner.setPages(new CBViewHolderCreator<BannerHolderView>() {

            @Override
            public BannerHolderView createHolder() {
                return new BannerHolderView();
            }
        }, banners).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected})
        .setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "banner: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        tvScroll.setText(16, DisplayUtils.dip2px(getActivity(), 10), Color.BLACK);//设置属性,具体跟踪源码
        tvScroll.setTextStillTime(3000);//设置停留时长间隔
        tvScroll.setAnimTime(500);//设置进入和退出的时间间隔
        //对单条文字的点击监听
        tvScroll.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(Object contentVo, int position) {
                Toast.makeText(getActivity(), contentVo.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        List<String> texts = new ArrayList<>();
        texts.add("text 0");
        texts.add("text 1");
        texts.add("text 2");
        texts.add("text 3");
        tvScroll.setTextList(texts);


        List<String> titles = new ArrayList<>();
        titles.add("0");
        titles.add("1");
        titles.add("2");
        titles.add("3");
        GridView gridView = new GridView(getActivity());
        gridView.setNumColumns(4);
        gridView.setAdapter(new QuickAdapter<String>(getActivity(), R.layout.girdview_item_home_pager, titles) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        });
        gridViews.add(gridView);
        GridView gridView1 = new GridView(getActivity());
        gridView1.setNumColumns(4);
        gridView1.setAdapter(new QuickAdapter<String>(getActivity(), R.layout.girdview_item_home_pager, titles) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        });
        gridViews.add(gridView1);
        homePager.setAdapter(new ViewPagerAdapter(gridViews));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return gridViews.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                ImageView imageView = new ImageView(context);
                imageView.setPadding(DisplayUtils.dip2px(context, 2.5F), 0, DisplayUtils.dip2px(context, 2.5F), 0);
                CommonPagerTitleView titleView = new CommonPagerTitleView(context){
                    @Override
                    public void onSelected(int index, int totalCount) {
                        super.onSelected(index, totalCount);
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.shape_indicator_selected));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        super.onDeselected(index, totalCount);
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.shape_indicator_normal));
                    }
                };
                titleView.addView(imageView);
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        homeIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(homeIndicator, homePager);

        barColorWithScroll();
    }

    private void barColorWithScroll() {
        int dp180 = (int) getResources().getDimension(R.dimen.dp_180);
        svContent.setOnScrollListener(new ObservableScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if(scrollY <  dp180){
                    layoutHomeBar.getBackground().setAlpha((int) (scrollY * 255F / dp180));
                } else {
                    layoutHomeBar.getBackground().setAlpha(255);
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if(homeBanner != null) {
            homeBanner.stopTurning();
        }
        if(tvScroll != null) {
            tvScroll.stopAutoScroll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(homeBanner != null) {
            homeBanner.startTurning(3 * 1000);
        }
        if(tvScroll != null) {
            tvScroll.startAutoScroll();
        }
    }

    class BannerHolderView implements Holder<String>{

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Application.application.getImageLoader().displayImage(data, imageView);
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_home;
    }

    @OnClick(R.id.ib_scan)
    void scanClick(View v){

    }

    @OnClick(R.id.layout_msg)
    void msgClick(View v){

    }
}
