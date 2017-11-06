package cn.com.mangopi.android.model.bean;

import java.io.Serializable;
import java.util.Date;

public class AppSignBean implements Serializable {

    private Date service_time;
    private AppVisionBean app_version;

    public Date getService_time() {
        return service_time;
    }

    public void setService_time(Date service_time) {
        this.service_time = service_time;
    }

    public AppVisionBean getApp_version() {
        return app_version;
    }

    public void setApp_version(AppVisionBean app_version) {
        this.app_version = app_version;
    }
}
