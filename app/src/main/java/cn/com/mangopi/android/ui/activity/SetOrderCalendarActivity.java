package cn.com.mangopi.android.ui.activity;

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
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.DisplayUtils;

public class SetOrderCalendarActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener{

    @Bind(R.id.gv_calendar)
    GridView gvCalendar;
    Calendar currentCalendar;
    List<String> datas = new ArrayList<>();
    QuickAdapter<String> calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_order_calendar);

        initView();
        initData();
    }

    private void initData() {
        currentCalendar = Calendar.getInstance();
        Calendar firstDayCalendar = Calendar.getInstance();
        firstDayCalendar.set(Calendar.DATE, 1);
        int weekDay = firstDayCalendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = currentCalendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        fillDatas(weekDay, dayOfMonth);

        int width = (DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15) * 2 - DisplayUtils.dip2px(this, 0.5F) * 6) / 7;
        gvCalendar.setAdapter(calendarAdapter = new QuickAdapter<String>(this, R.layout.gridview_item_order_calendar, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_day, item);
                if(!TextUtils.isEmpty(item) && (helper.getPosition() % 7 == 5 || helper.getPosition() % 7 == 6)){
                    helper.setTextColorRes(R.id.tv_day, R.color.color_ffb900);
                } else {
                    helper.setTextColorRes(R.id.tv_day, R.color.color_333333);
                }
                FrameLayout layoutDay = helper.getView(R.id.layout_day);
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
}
