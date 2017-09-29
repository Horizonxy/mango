package cn.com.mangopi.android.ui.viewlistener;

import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.bean.ScheduleCalendarBean;

public interface OrderScheduleCalendarListener extends BaseViewListener {
    void onScheduleCanlendarSuccess(List<ScheduleCalendarBean> scheduleCalendarList);
    Map<String, Object> getQueryMap();
}
