package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.R;
import com.mango.ui.adapter.ViewPagerAdapter;
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

public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_indicator)
    MagicIndicator tabIndicator;
    @Bind(R.id.view_pager)
    ViewPager contentPager;

    String[] tabTitles;
    List<View> contents = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tabTitles = getResources().getStringArray(R.array.main_tab);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        contents.add(tv1);
        contents.add(tv2);
        contents.add(tv3);
        contents.add(tv4);
        for (int i = 0; i < tabTitles.length; i++ ){
            ((TextView)contents.get(i)).setText(tabTitles[i]);
        }

        contentPager.setAdapter(new ViewPagerAdapter(contents));
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
                LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.item_tab_main_indicator, null, false);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DisplayUtils.screenWidth(context) / contents.size(), (int) getResources().getDimension(R.dimen.dp_49));
                params.gravity = Gravity.CENTER;
                titleView.setContentView(view, params);
                ImageButton ibImage = (ImageButton) titleView.findViewById(R.id.ib_tab_image);
                TextView tvText = (TextView) titleView.findViewById(R.id.tv_tab_text);
                tvText.setText(tabTitles[i]);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contentPager.setCurrentItem(i);
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
        ViewPagerHelper.bind(tabIndicator, contentPager);
    }


}
