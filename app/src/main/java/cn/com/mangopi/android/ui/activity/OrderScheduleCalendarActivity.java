package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ScheduleCalendarBean;
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
    Calendar systemCalendar;
    List<ScheduleCalendarBean> datas = new ArrayList<ScheduleCalendarBean>();
    QuickAdapter<ScheduleCalendarBean> calendarAdapter;
    boolean today;
    ScheduleCalendarPresenter scheduleCalendarPresenter;
    private long courseId;
    private long orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_schedule_calendar);

        courseId = getIntent().getLongExtra(Constants.BUNDLE_COURSE_ID, 0);
        orderId = getIntent().getLongExtra(Constants.BUNDLE_ORDER_ID, 0);
        initView();
        scheduleCalendarPresenter = new ScheduleCalendarPresenter(this);
        scheduleCalendarPresenter.scheduleCalendar();
        initData();
    }

    private void initData() {
        systemCalendar = Calendar.getInstance();

        int width = (DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15) * 2 - DisplayUtils.dip2px(this, 0.5F) * 6) / 7;
        gvCalendar.setAdapter(calendarAdapter = new QuickAdapter<ScheduleCalendarBean>(this, R.layout.gridview_item_order_calendar, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, ScheduleCalendarBean item) {

                FrameLayout layoutDay = helper.getView(R.id.layout_day);
                if(TextUtils.isEmpty(item.getDate())){
                    helper.setVisible(R.id.tv_day, false);
                } else {
                    helper.setText(R.id.tv_day, item.getDate())
                        .setVisible(R.id.tv_day, true);
                    if (today && null != item && (systemCalendar.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(item.getDate()))) {
                        helper.setTextColorRes(R.id.tv_day, R.color.white);
                        layoutDay.setBackgroundResource(R.color.color_ffb900);
                    } else if (null != item && (helper.getPosition() % 7 == 5 || helper.getPosition() % 7 == 6)) {
                        helper.setTextColorRes(R.id.tv_day, R.color.color_ffb900);
                        layoutDay.setBackgroundResource(R.color.white);
                    } else {
                        helper.setTextColorRes(R.id.tv_day, R.color.color_333333);
                        layoutDay.setBackgroundResource(R.color.white);
                    }
                }
                AbsListView.LayoutParams params = (AbsListView.LayoutParams) layoutDay.getLayoutParams();
                params.width = params.height = width;
                layoutDay.setLayoutParams(params);
            }
        });
    }

    private void initView() {
        titleBar.setRightText("今天");
        titleBar.setOnTitleBarClickListener(this);
    }

    @Override
    public void onTitleButtonClick(View view) {
       if(view.getId() == R.id.tv_right){

       }
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public void onScheduleCanlendarSuccess(List<ScheduleCalendarBean> scheduleCalendarList) {
        datas.clear();
        Calendar currentCalendar = Calendar.getInstance();
        titleBar.setTitle(DateUtils.calendarToString(currentCalendar, DateUtils.DATE_MONTH_CN));
        currentCalendar.set(Calendar.DATE, 1);
        int weekDay = currentCalendar.get(Calendar.DAY_OF_WEEK);

        if(weekDay == 1){
            for (int i = 0; i < 6; i++){
                datas.add(new ScheduleCalendarBean());
            }
        } else {
            for (int i = 0; i < (weekDay - 2); i++){
                datas.add(new ScheduleCalendarBean());
            }
        }

        List<ScheduleCalendarBean> month = new ArrayList<ScheduleCalendarBean>();
        for (int i = 0; i < currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
            String date = String.valueOf(i + 1);
            boolean hasRs = false;
            for (int j = 0; scheduleCalendarList != null && j < scheduleCalendarList.size(); j++){
                if(date.equals(scheduleCalendarList.get(j).getDate())){
                    month.add(scheduleCalendarList.get(j));
                    hasRs = true;
                    break;
                }
            }
            if(!hasRs) {
                ScheduleCalendarBean calendarBean = new ScheduleCalendarBean();
                calendarBean.setDate(date);
                month.add(calendarBean);
            }
        }

        datas.addAll(month);
        Logger.e(""+datas.size()+" "+(7 - (datas.size() % 7)));
        for(int i = 0; (datas.size() % 7) != 0 && i < (7 - (datas.size() % 7)); i++){
            datas.add(new ScheduleCalendarBean());
        }

        calendarAdapter.notifyDataSetChanged();

        today = (systemCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
                (systemCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)));
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        if(courseId > 0) {
            map.put("course_id", courseId);
        }
        if(orderId > 0) {
            map.put("order_id", orderId);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        map.put("start_date", DateUtils.calendarToString(calendar, DateUtils.DATE_PATTERN));
        map.put("month_limit", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return map;
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
