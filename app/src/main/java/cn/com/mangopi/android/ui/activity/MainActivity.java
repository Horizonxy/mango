package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;

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
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.data.MessageModel;
import cn.com.mangopi.android.presenter.MessagePresenter;
import cn.com.mangopi.android.ui.adapter.FragmentAdapter;
import cn.com.mangopi.android.ui.fragment.FoundFragment;
import cn.com.mangopi.android.ui.fragment.HomeFragment;
import cn.com.mangopi.android.ui.fragment.MyFragment;
import cn.com.mangopi.android.ui.fragment.TecaherFragment;
import cn.com.mangopi.android.ui.viewlistener.MessageListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MaskUtils;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements MessageListener, View.OnClickListener {

    @Bind(R.id.tab_indicator)
    MagicIndicator tabIndicator;
    @Bind(R.id.view_pager)
    ViewPager contentPager;

    String[] tabTitles;
    List<Fragment> contents = new ArrayList<>();
    MessagePresenter messagePresenter;
    Handler messageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initPush();
        messagePresenter = new MessagePresenter(new MessageModel(), this);
        messageHandler = new Handler();
        messageHandler.postDelayed(new MessageCheckRunnable(), 3 * 1000);

        MaskUtils.attachMaskFromRes(this, R.layout.layout_image_mask, R.id.iv_mask_del, this, R.id.iv_mask_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_mask_pic:

                break;
        }
    }

    class MessageCheckRunnable implements Runnable {

        @Override
        public void run() {
            messagePresenter.getMessageCheck();
            messageHandler.postDelayed(this, 3 * 60 * 1000);
        }
    }

    private void initPush() {
        JPushInterface.resumePush(this);
//        String rid = JPushInterface.getRegistrationID(getApplicationContext());
//        Logger.d("jpush registration id: " + rid);
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
        ViewPagerHelper.bind(tabIndicator, contentPager);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }

    @Override
    public void onFailure(String message) {}

    @Override
    public Context currentContext() {
        return null;
    }

    @Override
    public int getPageNo() {
        return 0;
    }

    @Override
    public void onSuccess(List<MessageBean> messageList) {}

    @Override
    public void onHasMessage(boolean hasMessage) {
        if(hasMessage){
            BusEvent.HasMessageEvent event = new BusEvent.HasMessageEvent();
            event.setHasMessage(true);
            Bus.getDefault().postSticky(event);
        }
    }

    @Override
    public void readMessageSuccess() {}

    @Override
    protected void onDestroy() {
        if(messageHandler != null && messageHandler.getLooper() == Looper.getMainLooper()){
            messageHandler.removeCallbacksAndMessages(null);
        }
        if(messagePresenter != null){
            messagePresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQ_SCAN:
                if (resultCode == RESULT_OK && data != null) {
                    String result = data.getStringExtra(Constants.BUNDLE_SCAN_RESULT);
                    if(result != null && result.startsWith("http")){
                        ActivityBuilder.startWebViewActivity(this, result);
                    }
                }
                break;

            default:
                break;
        }
    }
}
