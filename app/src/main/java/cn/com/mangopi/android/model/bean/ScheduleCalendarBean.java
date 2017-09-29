package cn.com.mangopi.android.model.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ScheduleCalendarBean implements Serializable {

    private Date sct_date;
    private boolean salable;
    private boolean cur_join;
    private String cause;
    private List<Details> details;
    private String date;
    private boolean clicked;

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String getDate(){
        if(TextUtils.isEmpty(date) && sct_date == null){
            date = "";
        }
        if(date == null) {
            date = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(sct_date);
        }
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getSct_date() {
        return sct_date;
    }

    public void setSct_date(Date sct_date) {
        this.sct_date = sct_date;
    }

    public boolean isSalable() {
        return salable;
    }

    public void setSalable(boolean salable) {
        this.salable = salable;
    }

    public boolean isCur_join() {
        return cur_join;
    }

    public void setCur_join(boolean cur_join) {
        this.cur_join = cur_join;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public static class Details implements Serializable{
        private Date sct_date;
        private boolean salable;
        private boolean cur_join;
        private String cause;
        private int count;

        public Date getSct_date() {
            return sct_date;
        }

        public void setSct_date(Date sct_date) {
            this.sct_date = sct_date;
        }

        public boolean isSalable() {
            return salable;
        }

        public void setSalable(boolean salable) {
            this.salable = salable;
        }

        public boolean isCur_join() {
            return cur_join;
        }

        public void setCur_join(boolean cur_join) {
            this.cur_join = cur_join;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
