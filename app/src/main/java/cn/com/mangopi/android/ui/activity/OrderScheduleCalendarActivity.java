package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.ScheduleCalendarBean;
import cn.com.mangopi.android.presenter.ScheduleCalendarPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.OrderScheduleCalendarListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;

public class OrderScheduleCalendarActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener,
        OrderScheduleCalendarListener, AdapterView.OnItemClickListener {

    @Bind(R.id.gv_calendar)
    GridView gvCalendar;
    Calendar systemCalendar;
    List<ScheduleCalendarBean> datas = new ArrayList<ScheduleCalendarBean>();
    QuickAdapter<ScheduleCalendarBean> calendarAdapter;
    ScheduleCalendarPresenter scheduleCalendarPresenter;
    long courseId;
    long orderId;
    ScheduleCalendarBean clickedSchedule;
    @Bind(R.id.layout_bottom)
    RelativeLayout layoutBottom;
    @Bind(R.id.tv_time_tip)
    TextView tvTimeTip;
    @Bind(R.id.gv_sct_time)
    GridView gvSctTime;
    List<ScheduleCalendarBean.Details> times = new ArrayList<ScheduleCalendarBean.Details>();
    QuickAdapter<ScheduleCalendarBean.Details> timeAdapter;
    ScheduleCalendarBean.Details selectedDetaill;
    Calendar currentCalendar;
    @Bind(R.id.btn_add_schedule)
    Button btnAddSchedule;
    @Bind(R.id.btn_reset)
    Button btnReset;
    @Bind(R.id.btn_to_order_by_schedule)
    Button btnOrderBySchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_schedule_calendar);

        courseId = getIntent().getLongExtra(Constants.BUNDLE_COURSE_ID, 0);
        orderId = getIntent().getLongExtra(Constants.BUNDLE_ORDER_ID, 0);
        initView();
        scheduleCalendarPresenter = new ScheduleCalendarPresenter(this);
        initData();
        scheduleCalendarPresenter.scheduleCalendar();
    }

    private void initData() {
        systemCalendar = Calendar.getInstance();

        int width = (DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15) * 2 - DisplayUtils.dip2px(this, 0.5F) * 6) / 7;
        gvCalendar.setAdapter(calendarAdapter = new QuickAdapter<ScheduleCalendarBean>(this, R.layout.gridview_item_order_calendar, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, ScheduleCalendarBean item) {

                FrameLayout layoutDay = helper.getView(R.id.layout_day);
                if(TextUtils.isEmpty(item.getDate())){
                    helper.setVisible(R.id.tv_day, false).setVisible(R.id.iv_course_icon, false);
                } else {
                    helper.setText(R.id.tv_day, item.getDate()).setVisible(R.id.tv_day, true);
                    if (clickedSchedule == item) {
                        helper.setTextColorRes(R.id.tv_day, R.color.white);
                        layoutDay.setBackgroundResource(R.color.color_ffb900);
                    } else if (helper.getPosition() % 7 == 5 || helper.getPosition() % 7 == 6) {
                        helper.setTextColorRes(R.id.tv_day, R.color.color_ffb900);
                        layoutDay.setBackgroundResource(R.color.white);
                    } else {
                        helper.setTextColorRes(R.id.tv_day, R.color.color_333333);
                        layoutDay.setBackgroundResource(R.color.white);
                    }
                    helper.setVisible(R.id.iv_course_icon, item.isCur_join());
                }
                AbsListView.LayoutParams params = (AbsListView.LayoutParams) layoutDay.getLayoutParams();
                params.width = params.height = width;
                layoutDay.setLayoutParams(params);
            }
        });
        gvCalendar.setOnItemClickListener(this);

        gvSctTime.setAdapter(timeAdapter = new QuickAdapter<ScheduleCalendarBean.Details>(this, R.layout.gridview_item_order_schedule_calendar_time, times) {
            @Override
            protected void convert(BaseAdapterHelper helper, ScheduleCalendarBean.Details item) {
                helper.setText(R.id.tv_time, item.getSct_time() + "点");
                View layout = helper.getView();
                if(item.isSelected()){
                    layout.setBackgroundResource(R.drawable.shape_border_order_schedule_calendar_time_selected);
                } else if(item.isSalable()){
                    layout.setBackgroundResource(R.drawable.shape_border_order_schedule_calendar_time_unselected);
                } else {
                    layout.setBackgroundResource(R.drawable.shape_border_order_schedule_calendar_time_none);
                }
            }
        });
        gvSctTime.setOnItemClickListener(this);

        currentCalendar = Calendar.getInstance();
    }

    private void initView() {
        titleBar.setRightText("今天");
        titleBar.setOnTitleBarClickListener(this);

        if(courseId == 0 || orderId == 0){
            btnReset.setVisibility(View.VISIBLE);
            btnAddSchedule.setVisibility(View.GONE);
            btnOrderBySchedule.setVisibility(View.VISIBLE);
            tvTimeTip.setText("计划上课时间");
        } else {
            btnReset.setVisibility(View.GONE);
            btnAddSchedule.setVisibility(View.VISIBLE);
            btnOrderBySchedule.setVisibility(View.GONE);
            tvTimeTip.setText("选择上课时间");
        }
    }

    @Override
    public void onTitleButtonClick(View view) {
       if(view.getId() == R.id.tv_right){
           currentCalendar.add(Calendar.MONTH, 1);
           currentCalendar.set(Calendar.DATE, 1);
           scheduleCalendarPresenter.scheduleCalendar();
       }
    }

    @OnClick(R.id.btn_add_schedule)
    void onAddScheduleClick(View v){
        if(selectedDetaill == null){
            AppUtils.showToast(this, "请选择上课时间");
            return;
        }
        scheduleCalendarPresenter.addOrderSchedule();
    }

    @OnClick(R.id.btn_reset)
    void onCancelBatchScheduleClick(View v){
        if(selectedDetaill == null){
            AppUtils.showToast(this, "请选择需重新安排的上课时间");
            return;
        }
        scheduleCalendarPresenter.cancelOrderBatchSchedule();
    }

    @OnClick(R.id.btn_to_order_by_schedule)
    void onOrderByScheduleClick(View v){
        if(selectedDetaill == null){
            AppUtils.showToast(this, "请选择上课时间");
            return;
        }
        ActivityBuilder.startScheduleOrderListActivity(this, getScheduleDate(), getScheduleTime());
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public void onScheduleCanlendarSuccess(List<ScheduleCalendarBean> scheduleCalendarList) {
        datas.clear();
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

        boolean today = (systemCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
                (systemCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)));
        if(today){
            int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            clickedSchedule = month.get(nowDay - 1);
        } else {
            clickedSchedule = month.get(0);
        }
        invalidateTimes(clickedSchedule);

        datas.addAll(month);
        for(int i = 0; (datas.size() % 7) != 0 && i < (7 - (datas.size() % 7)); i++){
            datas.add(new ScheduleCalendarBean());
        }

        calendarAdapter.notifyDataSetChanged();
    }

    private void invalidateTimes(ScheduleCalendarBean scheduleCalendar){
        times.clear();
        List<ScheduleCalendarBean.Details> details = scheduleCalendar.getDetails();
        for (int i = 8; i < 20; i++){
            boolean hasRs = false;
            for (int j = 0; details != null && j < details.size(); j++){
                ScheduleCalendarBean.Details detail = details.get(j);
                if(detail.getSct_time() == i) {
                    times.add(detail);
                    hasRs = true;
                    break;
                }
            }
            if(!hasRs) {
                ScheduleCalendarBean.Details detail = new ScheduleCalendarBean.Details();
                detail.setSct_time(i);
                detail.setSct_time(i);
                times.add(detail);
            }
        }
        timeAdapter.notifyDataSetChanged();
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
        currentCalendar.set(Calendar.DATE, 1);
        map.put("start_date", DateUtils.calendarToString(currentCalendar, DateUtils.DATE_PATTERN));
        map.put("month_limit", currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return map;
    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public String getScheduleDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, Integer.parseInt(clickedSchedule.getDate()));
        return DateUtils.calendarToString(calendar, DateUtils.DATE_PATTERN);
    }

    @Override
    public int getScheduleTime() {
        return selectedDetaill.getSct_time();
    }

    @Override
    public void onAddScheduleSuccess() {
        AppUtils.showToast(this, "安排课程已提交，请下拉刷新");
        finish();
    }

    @Override
    public void onCancelScheduleSuccess() {
        AppUtils.showToast(this, "重新安排课程已提交，请下拉刷新");
        finish();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj = parent.getAdapter().getItem(position);
        if(obj instanceof ScheduleCalendarBean) {
            ScheduleCalendarBean schedule = (ScheduleCalendarBean) obj;
            if (schedule != null && !TextUtils.isEmpty(schedule.getDate()) && schedule != clickedSchedule) {
                clickedSchedule = schedule;
                calendarAdapter.notifyDataSetChanged();
                invalidateTimes(schedule);
            }
        } else if(obj instanceof ScheduleCalendarBean.Details){
            ScheduleCalendarBean.Details details = (ScheduleCalendarBean.Details) obj;
            if(selectedDetaill != null){
                selectedDetaill.setSelected(false);
            }
            selectedDetaill = details;
            selectedDetaill.setSelected(true);
            timeAdapter.notifyDataSetChanged();
        }
    }
}
