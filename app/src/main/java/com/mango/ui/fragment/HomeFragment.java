package com.mango.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.di.component.DaggerHomeFragmentComponent;
import com.mango.di.module.HomeFragmentModule;
import com.mango.model.bean.AdvertBean;
import com.mango.presenter.HomePresenter;
import com.mango.ui.adapter.ViewPagerAdapter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.HomeFragmentListener;
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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeFragmentListener {

    @Bind(R.id.home_banner)
    ConvenientBanner homeBanner;
    List<AdvertBean> banners;
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
    @Bind(R.id.layout_update_role)
    RelativeLayout layoutUpdateRole;

    List<View> gridViews = new ArrayList<>();

    @Bind(R.id.tv_title1)
    TextView tvTitle1;
    @Bind(R.id.tv_intro1)
    TextView tvIntro1;
    @Bind(R.id.iv_advert1)
    ImageView ivAdvert1;
    @Bind(R.id.tv_title2)
    TextView tvTitle2;
    @Bind(R.id.tv_intro2)
    TextView tvIntro2;
    @Bind(R.id.layout_advert2)
    LinearLayout layoutAdvert2;
    @Bind(R.id.tv_title3)
    TextView tvTitle3;
    @Bind(R.id.iv_advert3)
    ImageView ivAdvert3;
    @Inject
    HomePresenter homePresenter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this)).build().inject(this);
    }

    @Override
    void initView() {
        banners = new ArrayList<>();
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
        gridView.setAdapter(new QuickAdapter<String>(getActivity(), R.layout.gridview_item_home_pager, titles) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {

            }
        });
        gridViews.add(gridView);
        GridView gridView1 = new GridView(getActivity());
        gridView1.setNumColumns(4);
        gridView1.setAdapter(new QuickAdapter<String>(getActivity(), R.layout.gridview_item_home_pager, titles) {
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

        initData();
    }

    private void initData() {
        homePresenter.getAdvert(Constants.INDEX_THREEE_ADVERT);

        homePresenter.getAdvert(Constants.INDEX_BANNER);
    }

    private void barColorWithScroll() {
        int dp224 = (int) getResources().getDimension(R.dimen.dp_224);
        svContent.setOnScrollListener(new ObservableScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if(layoutHomeBar.getBackground() != null) {
                    if (scrollY < dp224) {
                        layoutHomeBar.getBackground().setAlpha((int) (scrollY * 255F / dp224));
                    } else {
                        layoutHomeBar.getBackground().setAlpha(255);
                    }
                } else {
                    layoutHomeBar.setBackground(new ColorDrawable(getResources().getColor(R.color.color_ffb900)));
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

    @Override
    public void onSuccess(String position, List<AdvertBean> advertList) {
        if(Constants.INDEX_THREEE_ADVERT.equals(position)){
            if(advertList.size() > 0){
                AdvertBean advert1 = advertList.get(0);
                tvTitle1.setText(advert1.getTitle());
                tvIntro1.setText(advert1.getIntro());
                List<AdvertBean.DetailsBean> details = advert1.getDetails();
                if(details != null && details.size() > 0){
                    Application.application.getImageLoader().displayImage(details.get(0).getFile_path(), ivAdvert1, Application.application.getDefaultOptions());
                }
            } else {
                tvTitle1.setText("");
                tvIntro1.setText("");
                ivAdvert1.setImageResource(0);
            }
            if(advertList.size() > 1){
                AdvertBean advert2 = advertList.get(1);
                tvTitle2.setText(advert2.getTitle());
                tvIntro2.setText(advert2.getIntro());
                layoutAdvert2.removeAllViews();
                List<AdvertBean.DetailsBean> details = advert2.getDetails();
                int dp10 = (int) getResources().getDimension(R.dimen.dp_10);
                int width = (int) ((DisplayUtils.screenWidth(getContext()) - dp10 * 4) / 3.4);
                for (int i = 0; details != null && i < details.size(); i++){
                    ImageView item = new ImageView(getContext());
                    item.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(width, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                    item.setLayoutParams(params);
                    Application.application.getImageLoader().displayImage(details.get(i).getFile_path(), item, Application.application.getDefaultOptions());
                    layoutAdvert2.addView(item);
                }
            } else {
                tvTitle1.setText("");
                tvIntro1.setText("");
                layoutAdvert2.removeAllViews();
            }
            if(advertList.size() > 2){
                AdvertBean advert3 = advertList.get(2);
                tvTitle3.setText(advert3.getTitle());
                List<AdvertBean.DetailsBean> details = advert3.getDetails();
                if(details != null && details.size() > 0){
                    Application.application.getImageLoader().displayImage(details.get(0).getFile_path(), ivAdvert3, Application.application.getDefaultOptions());
                }
            } else {
                tvTitle3.setText("");
                ivAdvert3.setImageResource(0);
            }
        } else if(Constants.INDEX_BANNER.equals(position)) {
            banners.clear();
            banners.addAll(advertList);
            homeBanner.notifyDataSetChanged();
        }
    }

    @Override
    public String getUserIdentity() {
        return "public";
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    class BannerHolderView implements Holder<AdvertBean>{

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, AdvertBean data) {
            List<AdvertBean.DetailsBean> details = data.getDetails();
            if(details != null && details.size() > 0) {
                Application.application.getImageLoader().displayImage(details.get(0).getFile_path(), imageView, Application.application.getDefaultOptions());
            }
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
