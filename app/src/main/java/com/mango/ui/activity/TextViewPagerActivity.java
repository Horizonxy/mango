package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.R;

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

public class TextViewPagerActivity extends BaseTitleBarActivity {

    MagicIndicator indicator;
    ViewPager viewPager;
    List<TextView> tvList = new ArrayList<TextView>();
    List<String> tabTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_pager);

        initView();
    }

    private void initView() {
        titleBar.setTitle("Magic ViewPager");
        titleBar.setLeftBtnIcon(R.mipmap.ic_launcher);

        indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        TextView tv1 = new TextView(this);
        tv1.setText("全部");
        TextView tv2 = new TextView(this);
        tv2.setText("未付款");
        TextView tv3 = new TextView(this);
        tv3.setText("已付款");
        TextView tv4 = new TextView(this);
        tv4.setText("已完成");
        TextView tv5 = new TextView(this);
        tv5.setText("已取消");
        tvList.add(tv1);
        tvList.add(tv2);
        tvList.add(tv3);
        tvList.add(tv4);
        tvList.add(tv5);
        tabTitles.add("全部");
        tabTitles.add("未付款");
        tabTitles.add("已付款");
        tabTitles.add("已完成");
        tabTitles.add("已取消");

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return tvList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup view, int position) {
                view.addView(tvList.get(position));
                return tvList.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup view, int position, Object object) {
                view.removeView(tvList.get(position));
            }
        });

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tvList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setTextSize(16);
                titleView.setNormalColor(getResources().getColor(R.color.color_666666));
                titleView.setSelectedColor(getResources().getColor(R.color.color_ffb900));
                titleView.setWidth(getWindowManager().getDefaultDisplay().getWidth() / tabTitles.size());
                titleView.setText(tabTitles.get(i));
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(getResources().getColor(R.color.color_ffb900));
                indicator.setLineHeight(getResources().getDimension(R.dimen.dp_0_5));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }
}
