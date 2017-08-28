package cn.com.mangopi.android.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerHomeFragmentComponent;
import cn.com.mangopi.android.di.module.HomeFragmentModule;
import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.presenter.HomePresenter;
import cn.com.mangopi.android.ui.adapter.ViewPagerAdapter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.MultiItemTypeSupport;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.HomeFragmentListener;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.ui.widget.ObservableScrollView;
import cn.com.mangopi.android.ui.widget.RedPointView;
import cn.com.mangopi.android.ui.widget.VerticalTextview;
import cn.com.mangopi.android.ui.widget.ViewPagerFixed;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class HomeFragment extends BaseFragment implements HomeFragmentListener, View.OnClickListener {

    MangoPtrFrameLayout refreshLayout;
    ConvenientBanner homeBanner;
    List<AdvertBean> banners;
    VerticalTextview tvScroll;
    ViewPagerFixed homePager;
    LinearLayout homeIndicator;
    ObservableScrollView svContent;
    RelativeLayout layoutHomeBar;
    RelativeLayout layoutUpdateRole;
    List<View> gridViews = new ArrayList<>();

    @Inject
    HomePresenter homePresenter;
    AdvertDetaiClickListener advertDetaiClickListener;
    RedPointView messagePoint;
    LinearLayout layoutTwoGroup;
    ImageView ivGroup1, ivGroup2;

    ListView lvAdverts;
    ArrayList<AdvertBean> advertList = new ArrayList<>();
    QuickAdapter<AdvertBean> advertAdapter;

    EditText etSearch;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bus.getDefault().register(this);
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this)).build().inject(this);
    }

    @Override
    void findView(View root) {
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !(svContent.getScrollY() > 0);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(Application.application.getMember() != null) {
                    initData();
                }
            }
        });
        homeBanner = (ConvenientBanner) root.findViewById(R.id.home_banner);
        tvScroll = (VerticalTextview) root.findViewById(R.id.tv_scroll);
        homePager = (ViewPagerFixed) root.findViewById(R.id.home_pager);
        homeIndicator = (LinearLayout) root.findViewById(R.id.home_indicator);
        svContent = (ObservableScrollView) root.findViewById(R.id.sv_content);
        layoutUpdateRole = (RelativeLayout) root.findViewById(R.id.layout_update_role);
        root.findViewById(R.id.btn_update_info).setOnClickListener(this);

        layoutHomeBar = (RelativeLayout) root.findViewById(R.id.layout_home_bar);
        root.findViewById(R.id.ib_scan).setOnClickListener(this);
        root.findViewById(R.id.iv_message).setOnClickListener(this);
        root.findViewById(R.id.iv_bottom_del).setOnClickListener(this);
        messagePoint = (RedPointView) root.findViewById(R.id.point_message);

        advertDetaiClickListener = new AdvertDetaiClickListener(getActivity());

        layoutTwoGroup = (LinearLayout) root.findViewById(R.id.layout_two_group);
        ivGroup1 = (ImageView) root.findViewById(R.id.iv_group1);
        ivGroup2 = (ImageView) root.findViewById(R.id.iv_group2);
        ivGroup1.setOnClickListener(advertDetaiClickListener);
        ivGroup2.setOnClickListener(advertDetaiClickListener);

        lvAdverts = (ListView) root.findViewById(R.id.lv_adverts);

        root.findViewById(R.id.iv_tab_search).setOnClickListener(this);
        etSearch = (EditText) root.findViewById(R.id.et_search);
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
                        AdvertBean.DetailsBean advertDetail = banners.get(position).getDetails().get(0);
                        if(advertDetail != null) {
                            MangoUtils.jumpAdvert(HomeFragment.this.getActivity(), advertDetail);
                        }
                    }
                });

        tvScroll.setText(16, DisplayUtils.dip2px(getActivity(), 10), getResources().getColor(R.color.color_333333));//设置属性,具体跟踪源码
        tvScroll.setTextStillTime(2 * 1000);//设置停留时长间隔
        tvScroll.setAnimTime(400);//设置进入和退出的时间间隔
        //对单条文字的点击监听
        tvScroll.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(Object contentVo, int position) {
                ActivityBuilder.startBulletinDetailActivity(getActivity(), ((BulletinBean) contentVo).getId());
            }
        });

        lvAdverts.setAdapter(advertAdapter = new QuickAdapter<AdvertBean>(getContext(), advertList, new MultiItemTypeSupport<AdvertBean>() {
            @Override
            public int getLayoutId(int position, AdvertBean advertBean) {
                int type = getItemViewType(position, advertBean);
                if(1 == type){
                    return R.layout.layout_home_setting_one;
                } else if(2 == type){
                    return R.layout.layout_home_setting_more;
                }
                return R.layout.layout_home_setting_advert;
            }

            @Override
            public int getViewTypeCount() {
                return 3;
            }

            @Override
            public int getItemViewType(int postion, AdvertBean advertBean) {
                if("1".equals(advertBean.getType())){
                    return 1;
                } else if("2".equals(advertBean.getType()) || "3".equals(advertBean.getType())){
                    return 2;
                }
                return 3;
            }
        }) {
            @Override
            protected void convert(BaseAdapterHelper helper, AdvertBean item) {
                switch (helper.layoutId){
                    case R.layout.layout_home_setting_one:
                        helper.setText(R.id.tv_title1, item.getTitle())
                                .setText(R.id.tv_intro1, item.getIntro())
                                .setImageResource(R.id.iv_advert1, 0);
                        List<AdvertBean.DetailsBean> details = item.getDetails();
                        if (details != null && details.size() > 0) {
                            helper.setImageUrl(R.id.iv_advert1, details.get(0).getFile_path());
                            helper.setTag(R.id.iv_advert1, details.get(0));
                        }else {
                            helper.setTag(R.id.iv_advert1, null);
                        }
                        helper.setOnClickListener(R.id.iv_advert1, advertDetaiClickListener);
                        break;
                    case R.layout.layout_home_setting_more:
                        helper.setText(R.id.tv_title2, item.getTitle())
                                .setText(R.id.tv_intro2, item.getIntro());
                        LinearLayout layoutAdvert = helper.getView(R.id.layout_advert2);
                        layoutAdvert.removeAllViews();
                        details = item.getDetails();
                        int dp10 = (int) getResources().getDimension(R.dimen.dp_10);
                        int width = (int) ((DisplayUtils.screenWidth(getContext()) - dp10 * 4) / 3.4);
                        for (int j = 0; details != null && j < details.size(); j++) {
                            ImageView itemImageView = new ImageView(getContext());
                            itemImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(width, HorizontalScrollView.LayoutParams.MATCH_PARENT);
                            itemImageView.setLayoutParams(params);
                            Application.application.getImageLoader().displayImage(details.get(j).getFile_path(), itemImageView, Application.application.getDefaultOptions());
                            layoutAdvert.addView(itemImageView);
                            itemImageView.setTag(details.get(j));
                            itemImageView.setOnClickListener(advertDetaiClickListener);
                        }
                        break;
                    case R.layout.layout_home_setting_advert:
                        helper.setText(R.id.tv_title3, item.getTitle())
                                .setImageResource(R.id.iv_advert3, 0);

                        details = item.getDetails();
                        if (details != null && details.size() > 0) {
                            helper.setImageUrl(R.id.iv_advert3, details.get(0).getFile_path());
                            helper.setTag(R.id.iv_advert3, details.get(0));
                        } else {
                            helper.setTag(R.id.iv_advert3, null);
                        }
                        helper.setOnClickListener(R.id.iv_advert3, advertDetaiClickListener);
                        break;
                }
            }
        });

        refreshLayout.autoRefresh(true);
//        initData();

        barColorWithScroll();

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
        homePresenter.getAdvert(Constants.INDEX_TWO_ADVERT);
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
        refreshLayout.refreshComplete();
        if (Constants.INDEX_THREEE_ADVERT.equals(position)) {
            this.advertList.clear();
            this.advertList.addAll(advertList);
            advertAdapter.notifyDataSetChanged();
            refreshLayout.scrollTo(0, 0);
        } else if (Constants.INDEX_BANNER.equals(position)) {
            banners.clear();
            banners.addAll(advertList);
            homeBanner.notifyDataSetChanged();
        } else if(Constants.INDEX_TWO_ADVERT.equals(position)){
            if(advertList.size() > 0) {
                layoutTwoGroup.setVisibility(View.VISIBLE);
                List<AdvertBean.DetailsBean> details = advertList.get(0).getDetails();
                if (details.size() > 0) {
                    AdvertBean.DetailsBean detail1 = details.get(0);
                    Application.application.getImageLoader().displayImage(detail1.getFile_path(), ivGroup1, Application.application.getDefaultOptions());
                    ivGroup1.setTag(detail1);
                } else {
                    ivGroup1.setImageResource(0);
                    ivGroup1.setTag(null);
                }
                if (details.size() > 1) {
                    AdvertBean.DetailsBean detail2 = details.get(1);
                    Application.application.getImageLoader().displayImage(detail2.getFile_path(), ivGroup2, Application.application.getDefaultOptions());
                    ivGroup2.setTag(detail2);
                } else {
                    ivGroup2.setImageResource(0);
                    ivGroup2.setTag(null);
                }
            } else {
                layoutTwoGroup.setVisibility(View.GONE);
            }
        }
    }

    private static class AdvertDetaiClickListener implements View.OnClickListener{

        Activity activity;

        public AdvertDetaiClickListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {
            AdvertBean.DetailsBean advertDetail = (AdvertBean.DetailsBean) v.getTag();
            if(advertDetail != null) {
                MangoUtils.jumpAdvert(activity, advertDetail);
            }
        }
    }

    @Override
    public void onSuccess(List<BulletinBean> bulletinList) {
        refreshLayout.refreshComplete();
        tvScroll.setTextList(bulletinList);
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> courseClassifyList) {
        refreshLayout.refreshComplete();
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
            imageView.setBackgroundResource(android.R.color.transparent);
            homeIndicator.addView(imageView);
        }

        if(homePager.getAdapter() == null) {
            homePager.setAdapter(new ViewPagerAdapter(gridViews));
        } else {
            homePager.getAdapter().notifyDataSetChanged();
        }
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
        MemberBean member = Application.application.getMember();
        if(member != null){
            return member.getUser_identity_label();
        }
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
                ActivityBuilder.startSetOrderCalendarActivity(getActivity());
                break;
            case R.id.iv_message:
                ActivityBuilder.startMessageListActivity(getActivity());
                break;
            case R.id.iv_bottom_del:
                layoutUpdateRole.setVisibility(View.GONE);
                break;
            case R.id.btn_update_info:
                ActivityBuilder.startUpgradeRoleActivityy(getActivity());
                break;
            case R.id.iv_tab_search:
                if(!TextUtils.isEmpty(etSearch.getText())) {
                    ActivityBuilder.startSearchActivity(getActivity(), etSearch.getText().toString());
                }
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

    @BusReceiver
    public void onHasMessageEvent(BusEvent.HasMessageEvent event){
        if(event != null && messagePoint != null){
            messagePoint.setVisibility(event.isHasMessage() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (homePresenter != null) {
            homePresenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }


}
