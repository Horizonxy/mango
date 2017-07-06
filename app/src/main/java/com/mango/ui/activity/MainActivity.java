package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.R;
import com.mango.ui.adapter.FragmentAdapter;
import com.mango.ui.fragment.FoundFragment;
import com.mango.ui.fragment.HomeFragment;
import com.mango.ui.fragment.MyFragment;
import com.mango.ui.fragment.TecaherFragment;
import com.mango.util.DisplayUtils;
import com.mango.util.SystemStatusManager;

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

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_indicator)
    MagicIndicator tabIndicator;
    @Bind(R.id.view_pager)
    ViewPager contentPager;

    String[] tabTitles;
    List<Fragment> contents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setTranslucentStatusColor(this, getResources().getColor(R.color.color_ffb900));
        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView() {
        tabTitles = getResources().getStringArray(R.array.main_tab);
        contents.add(new HomeFragment());
        contents.add(new TecaherFragment());
        contents.add(new FoundFragment());
        contents.add(new MyFragment());

        contentPager.setAdapter(new FragmentAdapter(getSupportFragmentManager() ,contents));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return contents.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context){
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
                LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_item_main_indicator, null, false);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DisplayUtils.screenWidth(context) / contents.size(), (int) getResources().getDimension(R.dimen.dp_49));
                params.gravity = Gravity.CENTER;
                titleView.setContentView(view, params);
                ImageView ivTab = (ImageView) titleView.findViewById(R.id.iv_tab);
                if(i == 0){
                    ivTab.setImageResource(R.drawable.selector_btn_bg_home_tab_main_indicator);
                } else if(i == 1){
                    ivTab.setImageResource(R.drawable.selector_btn_bg_teacher_tab_main_indicator);
                } else if(i == 2){
                    ivTab.setImageResource(R.drawable.selector_btn_bg_found_tab_main_indicator);
                } else if(i == 3){
                    ivTab.setImageResource(R.drawable.selector_btn_bg_my_tab_main_indicator);
                }
                TextView tvText = (TextView) titleView.findViewById(R.id.tv_tab_text);
                tvText.setText(tabTitles[i]);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contentPager.setCurrentItem(i, false);
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
        contentPager.setOffscreenPageLimit(4);
        ViewPagerHelper.bind(tabIndicator, contentPager);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
