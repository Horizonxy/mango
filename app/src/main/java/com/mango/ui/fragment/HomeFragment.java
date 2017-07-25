package com.mango.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CommonBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.MemberBean;
import com.mango.model.db.CommonDaoImpl;
import com.mango.presenter.HomePresenter;
import com.mango.ui.adapter.ViewPagerAdapter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.HomeFragmentListener;
import com.mango.ui.widget.ObservableScrollView;
import com.mango.ui.widget.VerticalTextview;
import com.mango.ui.widget.ViewPagerFixed;
import com.mango.util.ActivityBuilder;
import com.mango.util.DisplayUtils;
import com.mango.util.MangoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment implements HomeFragmentListener, View.OnClickListener {

    ConvenientBanner homeBanner;
    List<AdvertBean> banners;
    VerticalTextview tvScroll;
    ViewPagerFixed homePager;
    LinearLayout homeIndicator;
    ObservableScrollView svContent;
    RelativeLayout layoutHomeBar;
    RelativeLayout layoutUpdateRole;
    List<View> gridViews = new ArrayList<>();
    TextView tvTitle1;
    TextView tvIntro1;
    ImageView ivAdvert1;
    TextView tvTitle2;
    TextView tvIntro2;
    LinearLayout layoutAdvert2;
    TextView tvTitle3;
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
    void findView(View root) {
        homeBanner = (ConvenientBanner) root.findViewById(R.id.home_banner);
        tvScroll = (VerticalTextview) root.findViewById(R.id.tv_scroll);
        homePager = (ViewPagerFixed) root.findViewById(R.id.home_pager);
        homeIndicator = (LinearLayout) root.findViewById(R.id.home_indicator);
        svContent = (ObservableScrollView) root.findViewById(R.id.sv_content);
        layoutUpdateRole = (RelativeLayout) root.findViewById(R.id.layout_update_role);
        tvTitle1 = (TextView) root.findViewById(R.id.tv_title1);
        tvIntro1 = (TextView) root.findViewById(R.id.tv_intro1);
        ivAdvert1 = (ImageView) root.findViewById(R.id.iv_advert1);
        tvTitle2 = (TextView) root.findViewById(R.id.tv_title2);
        tvIntro2 = (TextView) root.findViewById(R.id.tv_intro2);
        layoutAdvert2 = (LinearLayout) root.findViewById(R.id.layout_advert2);
        tvTitle3 = (TextView) root.findViewById(R.id.tv_title3);
        ivAdvert3 = (ImageView) root.findViewById(R.id.iv_advert3);
        layoutHomeBar = (RelativeLayout) root.findViewById(R.id.layout_home_bar);
        root.findViewById(R.id.ib_scan).setOnClickListener(this);
        root.findViewById(R.id.layout_msg).setOnClickListener(this);
        root.findViewById(R.id.iv_bottom_del).setOnClickListener(this);
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

        tvScroll.setText(16, DisplayUtils.dip2px(getActivity(), 10), getResources().getColor(R.color.color_333333));//设置属性,具体跟踪源码
        tvScroll.setTextStillTime(2 * 1000);//设置停留时长间隔
        tvScroll.setAnimTime(400);//设置进入和退出的时间间隔
        //对单条文字的点击监听
        tvScroll.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(Object contentVo, int position) {
                ActivityBuilder.startWebViewActivity(getActivity(), (BulletinBean) contentVo);
            }
        });

        barColorWithScroll();

        initData();

        List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
        if (!indentityList.contains(Constants.UserIndentity.PUBLIC)) {
            layoutUpdateRole.setVisibility(View.GONE);
        } else {
            layoutUpdateRole.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        homePresenter.getHomeBulletinList();
        homePresenter.getAdvert(Constants.INDEX_BANNER);
        homePresenter.getCourseClassify();
        homePresenter.getAdvert(Constants.INDEX_THREEE_ADVERT);
    }

    private void barColorWithScroll() {
        if (layoutHomeBar.getBackground() == null) {
            layoutHomeBar.setBackgroundResource(R.color.color_ffb900);
        }
        layoutHomeBar.getBackground().mutate().setAlpha(0);
        int dp224 = (int) getResources().getDimension(R.dimen.dp_224);
        svContent.setOnScrollListener(new ObservableScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY < dp224) {
                    layoutHomeBar.getBackground().mutate().setAlpha((int) (scrollY * 255F / dp224));
                } else {
                    layoutHomeBar.getBackground().mutate().setAlpha(255);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (homeBanner != null) {
            homeBanner.stopTurning();
        }
        if (tvScroll != null) {
            tvScroll.stopAutoScroll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeBanner != null) {
            homeBanner.startTurning(3 * 1000);
        }
        if (tvScroll != null) {
            tvScroll.startAutoScroll();
        }
    }

    @Override
    public void onSuccess(String position, List<AdvertBean> advertList) {
        if (Constants.INDEX_THREEE_ADVERT.equals(position)) {
            if (advertList.size() > 0) {
                AdvertBean advert1 = advertList.get(0);
                tvTitle1.setText(advert1.getTitle());
                tvIntro1.setText(advert1.getIntro());
                List<AdvertBean.DetailsBean> details = advert1.getDetails();
                if (details != null && details.size() > 0) {
                    Application.application.getImageLoader().displayImage(details.get(0).getFile_path(), ivAdvert1, Application.application.getDefaultOptions());
                }
            } else {
                tvTitle1.setText("");
                tvIntro1.setText("");
                ivAdvert1.setImageResource(0);
            }
            if (advertList.size() > 1) {
                AdvertBean advert2 = advertList.get(1);
                tvTitle2.setText(advert2.getTitle());
                tvIntro2.setText(advert2.getIntro());
                layoutAdvert2.removeAllViews();
                List<AdvertBean.DetailsBean> details = advert2.getDetails();
                int dp10 = (int) getResources().getDimension(R.dimen.dp_10);
                int width = (int) ((DisplayUtils.screenWidth(getContext()) - dp10 * 4) / 3.4);
                for (int i = 0; details != null && i < details.size(); i++) {
                    ImageView item = new ImageView(getContext());
                    item.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(width, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                    item.setLayoutParams(params);
                    Application.application.getImageLoader().displayImage(details.get(i).getFile_path(), item, Application.application.getDefaultOptions());
                    layoutAdvert2.addView(item);
                }
            } else {
                tvTitle2.setText("");
                tvIntro2.setText("");
                layoutAdvert2.removeAllViews();
            }
            if (advertList.size() > 2) {
                AdvertBean advert3 = advertList.get(2);
                tvTitle3.setText(advert3.getTitle());
                List<AdvertBean.DetailsBean> details = advert3.getDetails();
                if (details != null && details.size() > 0) {
                    Application.application.getImageLoader().displayImage(details.get(0).getFile_path(), ivAdvert3, Application.application.getDefaultOptions());
                }
            } else {
                tvTitle3.setText("");
                ivAdvert3.setImageResource(0);
            }
        } else if (Constants.INDEX_BANNER.equals(position)) {
            banners.clear();
            banners.addAll(advertList);
            homeBanner.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(List<BulletinBean> bulletinList) {
        tvScroll.setTextList(bulletinList);
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> courseClassifyList) {
        gridViews.clear();
        homeIndicator.removeAllViews();
        for (int i = 0; courseClassifyList != null && i < courseClassifyList.size(); i += 4) {
            List<CourseClassifyBean> pager = new ArrayList<CourseClassifyBean>();
            pager.addAll(courseClassifyList.subList(i, (i + 4) < courseClassifyList.size() ? (i + 4) : courseClassifyList.size()));

            GridView gridView = new GridView(getActivity());
            gridView.setNumColumns(4);
            gridView.setAdapter(new QuickAdapter<CourseClassifyBean>(getActivity(), R.layout.gridview_item_home_pager, pager) {
                @Override
                protected void convert(BaseAdapterHelper helper, CourseClassifyBean item) {
                    helper.setImageUrl(R.id.iv_classify, item.getLogo_rsurl());
                    helper.setText(R.id.tv_title, item.getClassify_name());
                }
            });
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CourseClassifyBean classify = (CourseClassifyBean) parent.getAdapter().getItem(position);
                    ActivityBuilder.startCalssListActivity(getActivity(), classify);
                }
            });
            gridViews.add(gridView);
            ImageView imageView = new ImageView(getContext());
            imageView.setPadding(DisplayUtils.dip2px(getContext(), 2.5F), 0, DisplayUtils.dip2px(getContext(), 2.5F), 0);
            imageView.setImageResource(R.drawable.shape_indicator_normal);
            homeIndicator.addView(imageView);
        }

        homePager.setAdapter(new ViewPagerAdapter(gridViews));
        homePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < homeIndicator.getChildCount(); i++) {
                    if (i == position) {
                        ((ImageView) homeIndicator.getChildAt(position)).setImageResource(R.drawable.shape_indicator_selected);
                    } else {
                        ((ImageView) homeIndicator.getChildAt(i)).setImageResource(R.drawable.shape_indicator_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ((ImageView) homeIndicator.getChildAt(homePager.getCurrentItem())).setImageResource(R.drawable.shape_indicator_selected);
    }

    @Override
    public String getUserIdentity() {
//        MemberBean member = Application.application.getMember();
//        if(member != null){
//            return member.getUser_identity_label();
//        }
        return "public";
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_scan:
                break;
            case R.id.layout_msg:
                break;
            case R.id.iv_bottom_del:
                layoutUpdateRole.setVisibility(View.GONE);
                break;
        }
    }

    class BannerHolderView implements Holder<AdvertBean> {

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
            if (details != null && details.size() > 0) {
                Application.application.getImageLoader().displayImage(details.get(0).getFile_path(), imageView, Application.application.getDefaultOptions());
            }
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter != null) {
            homePresenter.onDestroy();
        }
    }
}
