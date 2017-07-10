package com.mango.ui.activity;

import android.content.Context;
import android.graphics.Color;
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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class TabViewPagerActivity extends BaseTitleBarActivity {

    MagicIndicator indicator;
    ViewPager viewPager;
    List<TextView> tvList = new ArrayList<TextView>();
    List<String> tabTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_pager);

        initView();
    }

    private void initView() {
        titleBar.setTitle("Magic ViewPager");
        titleBar.setLeftBtnIcon(R.drawable.back);

        indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        TextView tv1 = new TextView(this);
        tv1.setText("我下的订单");
        TextView tv2 = new TextView(this);
        tv2.setText("我收到的订单");
        tvList.add(tv1);
        tvList.add(tv2);
        tabTitles.add("我下的订单");
        tabTitles.add("我收到的订单");

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
                SimplePagerTitleView titleView = new SimplePagerTitleView(context){
                    @Override
                    public void onSelected(int index, int totalCount) {
                        super.onSelected(index, totalCount);
                        this.setSelected(true);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        super.onDeselected(index, totalCount);
                        this.setSelected(false);
                    }
                };
                titleView.setTextSize(16);
                titleView.setNormalColor(Color.BLACK);
                titleView.setSelectedColor(Color.BLACK);
                titleView.setWidth((int) getResources().getDimension(R.dimen.dp_112));
                if(i == 0) {
                    titleView.setBackgroundResource(R.drawable.selector_bg_tab_left);
                } else if(i == (tabTitles.size() - 1)){
                    titleView.setBackgroundResource(R.drawable.selector_bg_tab_right);
                }
                titleView.setText(tabTitles.get(i));
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

}
