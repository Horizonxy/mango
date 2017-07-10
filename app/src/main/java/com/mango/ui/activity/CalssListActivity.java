package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.MemberCardBean;
import com.mango.ui.adapter.FragmentAdapter;
import com.mango.ui.fragment.ClassListFragment;

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

public class CalssListActivity extends BaseTitleBarActivity {

    @Bind(R.id.tab_indicator)
    MagicIndicator tabIndicator;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    List<String> tabTitles = new ArrayList<>();
    List<Fragment> viewLists = new ArrayList<>();

    List<MemberCardBean> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calss_list);

        cardList = (List<MemberCardBean>) getIntent().getSerializableExtra(Constants.BUNDLE_CARD_LIST);

        initView();
    }

    private void initView() {
        titleBar.setTitle("互联网+");

        for (int i = 0; i < 9; i++){
            tabTitles.add("category" + i);
            viewLists.add(new ClassListFragment());
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
                titleView.setTextSize(15);
                titleView.setNormalColor(getResources().getColor(R.color.color_666666));
                titleView.setSelectedColor(getResources().getColor(R.color.color_ffb900));

                titleView.setText(tabTitles.get(i));
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
        viewPager.setOffscreenPageLimit(2);
        ViewPagerHelper.bind(tabIndicator, viewPager);
    }
}
