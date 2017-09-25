package cn.com.mangopi.android.di.module;

import android.app.Activity;
import android.view.View;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.model.bean.TrendDetailBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.popupwindow.InputPopupWindow;
import cn.com.mangopi.android.util.DateUtils;
import dagger.Module;
import dagger.Provides;

@Module
public class TrendCommentsActivityModule {

    Activity activity;
    List datas;

    public TrendCommentsActivityModule(Activity activity, List datas) {
        this.activity = activity;
        this.datas = datas;
    }


    @ActivityScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<TrendDetailBean.Comment>(activity, R.layout.listview_item_interact_area, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, TrendDetailBean.Comment item) {
                helper.setText(R.id.tv_name, item.getMember_name())
                        .setText(R.id.tv_time, DateUtils.getShowTime(item.getComment_time()))
                        .setText(R.id.tv_content, item.getContent())
                .setOnClickListener(R.id.iv_comment, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputPopupWindow inputPopupWindow = new InputPopupWindow(activity, new InputPopupWindow.OnInputListener() {
                            @Override
                            public void onInput(String text) {

                            }
                        });
                        inputPopupWindow.showWindow();
                    }
                });
            }
        };
    }
}
