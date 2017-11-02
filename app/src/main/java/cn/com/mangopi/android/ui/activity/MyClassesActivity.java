package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.FragmentAdapter;
import cn.com.mangopi.android.ui.fragment.MyClassesFragment;
import cn.com.mangopi.android.ui.widget.ViewPagerFixed;
import cn.com.mangopi.android.util.ActivityBuilder;

public class MyClassesActivity extends BaseActivity {

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
        setContentView(R.layout.activity_my_classes);

        initView();
    }

    private void initView() {
        tabTitles = getResources().getStringArray(R.array.classes_tab);

        fragmentList.add(MyClassesFragment.newInstance(MyClassesFragment.TPYE_ALL));
        fragmentList.add(MyClassesFragment.newInstance(MyClassesFragment.TPYE_ON));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabIndicator.getLayoutParams();
        params.width = (int) (getResources().getDimension(R.dimen.dp_112) * 2);
        tabIndicator.setLayoutParams(params);

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
                titleView.setWidth((int) getResources().getDimension(R.dimen.dp_112));
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

    @OnClick(R.id.tv_add_class)
    void addClass(){
        ActivityBuilder.startAddCourseActivity(this);
    }
}
