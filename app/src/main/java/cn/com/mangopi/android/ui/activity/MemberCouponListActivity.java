package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.FragmentAdapter;
import cn.com.mangopi.android.ui.fragment.CouponListFragment;
import cn.com.mangopi.android.util.DisplayUtils;

public class MemberCouponListActivity extends BaseTitleBarActivity {

    @Bind(R.id.tab_indicator)
    MagicIndicator tabIndicator;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    List<Fragment> viewLists = new ArrayList<>();
    String[] tabTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_coupon_list);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.my_coupon);

        tabTitles = getResources().getStringArray(R.array.member_coupon);
        for (int i = 0; tabTitles != null && i < tabTitles.length; i++){
            viewLists.add(CouponListFragment.newInstance(tabTitles[i]));
        }
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), viewLists));

        initIndicatorViewPager();
    }

    private void initIndicatorViewPager() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return viewLists.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setTextSize(16);
                titleView.setWidth(DisplayUtils.screenWidth(MemberCouponListActivity.this) / tabTitles.length);
                titleView.setNormalColor(getResources().getColor(R.color.color_666666));
                titleView.setSelectedColor(getResources().getColor(R.color.color_ffb900));

                titleView.setText(tabTitles[i]);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i, false);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setLineHeight(getResources().getDimension(R.dimen.dp_2));
                indicator.setColors(getResources().getColor(R.color.color_ffb900));
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                return indicator;
            }
        });
        tabIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabIndicator, viewPager);
    }
}
