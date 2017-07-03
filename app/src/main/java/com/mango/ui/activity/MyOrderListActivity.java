package com.mango.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;

import com.mango.R;
import com.mango.ui.adapter.FragmentAdapter;
import com.mango.ui.fragment.MyOrderListFragment;
import com.mango.ui.widget.ViewPagerFixed;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MyOrderListActivity extends BaseFragmentActivity {

    @Bind(R.id.ib_left)
    ImageButton ibLeft;
    @Bind(R.id.tab_indicator)
    MagicIndicator tabIndicator;
    @Bind(R.id.view_pager)
    ViewPagerFixed viewPager;

    String[] tabTitles;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);

        initView();
    }

    private void initView() {
        tabTitles = getResources().getStringArray(R.array.order_tab);

        fragmentList.add(new MyOrderListFragment());
        fragmentList.add(new MyOrderListFragment());

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));

        initIndicatorViewPager();

    }

    private void initIndicatorViewPager() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList.size();
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
                titleView.setWidth((int) getResources().getDimension(R.dimen.dp_120));
                if(i == 0) {
                    titleView.setBackgroundResource(R.drawable.selector_bg_tab_left);
                } else if(i == (fragmentList.size() - 1)){
                    titleView.setBackgroundResource(R.drawable.selector_bg_tab_right);
                }
                titleView.setText(tabTitles[i]);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(i);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        tabIndicator.setNavigator(commonNavigator);
        viewPager.setOffscreenPageLimit(2);
        ViewPagerHelper.bind(tabIndicator, viewPager);
    }

    @OnClick(R.id.ib_left)
    void backClick(View v){
        finish();
    }
}
