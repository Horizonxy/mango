package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.presenter.ScheduleCalendarPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.OrderScheduleCalendarListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;

public class OrderScheduleCalendarActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener, OrderScheduleCalendarListener {

    @Bind(R.id.gv_calendar)
    GridView gvCalendar;
    Calendar currentCalendar;
    Calendar systemCalendar;
    List<String> datas = new ArrayList<>();
    QuickAdapter<String> calendarAdapter;
    boolean today;
    ScheduleCalendarPresenter scheduleCalendarPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_order_calendar);

        initView();
        scheduleCalendarPresenter = new ScheduleCalendarPresenter(this);
        scheduleCalendarPresenter.scheduleCalendar();
        initData();
    }

    private void initData() {
        systemCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        Calendar firstDayCalendar = Calendar.getInstance();
        firstDayCalendar.set(Calendar.DATE, 1);
        int weekDay = firstDayCalendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = currentCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        fillDatas(weekDay, dayOfMonth);
        titleBar.setTitle(DateUtils.calendarToString(firstDayCalendar, DateUtils.DATE_MONTH_CN));

        int width = (DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15) * 2 - DisplayUtils.dip2px(this, 0.5F) * 6) / 7;
        gvCalendar.setAdapter(calendarAdapter = new QuickAdapter<String>(this, R.layout.gridview_item_order_calendar, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_day, item);
                FrameLayout layoutDay = helper.getView(R.id.layout_day);
                if(today && !TextUtils.isEmpty(item) && (systemCalendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(item))){
                    helper.setTextColorRes(R.id.tv_day, R.color.white);
                    layoutDay.setBackgroundResource(R.color.color_ffb900);
                } else if(!TextUtils.isEmpty(item) && (helper.getPosition() % 7 == 5 || helper.getPosition() % 7 == 6)){
                    helper.setTextColorRes(R.id.tv_day, R.color.color_ffb900);
                    layoutDay.setBackgroundResource(R.color.white);
                } else {
                    helper.setTextColorRes(R.id.tv_day, R.color.color_333333);
                    layoutDay.setBackgroundResource(R.color.white);
                }
                AbsListView.LayoutParams params = (AbsListView.LayoutParams) layoutDay.getLayoutParams();
                params.width = params.height = width;
                layoutDay.setLayoutParams(params);
            }
        });
    }

    private void fillDatas(int weekDay, int dayOfMonth){
        datas.clear();
        if(weekDay == 1){
            for (int i = 0; i < 6; i++){
                datas.add("");
            }
        } else {
            for (int i = 0; i < (weekDay - 2); i++){
                datas.add("");
            }
        }

        for (int i = 0; i < dayOfMonth; i++){
//            currentCalendar.set(Calendar.DATE, i + 1 );
//            datas.add(DateUtils.calendarToString(currentCalendar, DateUtils.DATE_PATTERN));
            datas.add(String.valueOf(i + 1));
        }

        today = (systemCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
                (systemCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)));
    }

    private void initView() {
        titleBar.setRightText("今天");
        titleBar.setOnTitleBarClickListener(this);

    }

    @Override
    public void onTitleButtonClick(View view) {
       if(view.getId() == R.id.tv_right){
           currentCalendar.add(Calendar.MONTH , 1);
           currentCalendar.set(Calendar.DATE, 1);
           int weekDay = currentCalendar.get(Calendar.DAY_OF_WEEK);
           int dayOfMonth = currentCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
           fillDatas(weekDay, dayOfMonth);
           titleBar.setTitle(DateUtils.calendarToString(currentCalendar, DateUtils.DATE_MONTH_CN));
           calendarAdapter.notifyDataSetChanged();
       }
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if(scheduleCalendarPresenter != null){
            scheduleCalendarPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
