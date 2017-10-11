package cn.com.mangopi.android;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.mobstat.StatService;
import com.mcxiaoke.bus.Bus;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.PlatformConfig;

import java.util.List;

import javax.inject.Inject;

import cn.com.mangopi.android.di.component.DaggerAppComponent;
import cn.com.mangopi.android.di.module.ApiModule;
import cn.com.mangopi.android.di.module.AppModule;
import cn.com.mangopi.android.model.api.ApiService;
import cn.com.mangopi.android.model.bean.CommonBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.db.CommonDaoImpl;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.PreUtils;
import cn.com.mangopi.android.wxapi.WXEntryActivity;
import cn.jpush.android.api.JPushInterface;
import dagger.Lazy;

public class Application extends MultiDexApplication {

    public static Application application;

    @Inject
    Lazy<DisplayImageOptions> defaultOptions;
    @Inject
    Lazy<ImageLoader> imageLoader;
    @Inject
    Lazy<ApiService> apiService;

    @Inject
    Lazy<List<Activity>> activities;
    @Inject
    Lazy<CommonDaoImpl> commonDao;

    String sessId;
    MemberBean member;

    @Override
    public void onCreate() {
        super.onCreate();
        if(!BuildConfig.DEBUG) {
            AppUtils.initCarsh(this);
            Logger.init(getResources().getString(R.string.app_name));
        }
        DaggerAppComponent.builder().apiModule(new ApiModule()).appModule(new AppModule(this)).build().inject(this);
        application = this;

        StatService.setDebugOn(BuildConfig.DEBUG);
        String vendor = AppUtils.getChannel(this);
        StatService.setAppChannel(this, vendor, false);
        StatService.autoTrace(this, true, true);

        Bus.getDefault().setStrictMode(true);
        Bus.getDefault().setDebug(BuildConfig.DEBUG);

        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
    }

    //各个平台的配置
    {
        PlatformConfig.setWeixin(WXEntryActivity.WEIXIN_APP_ID, WXEntryActivity.APP_SECRET);
        PlatformConfig.setQQZone("1105462216", "bES9PBXDeWAj2bmq");
    }

    public DisplayImageOptions getDefaultOptions() {
        return defaultOptions.get();
    }

    public ImageLoader getImageLoader() {
        return imageLoader.get();
    }

    public ApiService getApiService(){
        return apiService.get();
    }

    public CommonDaoImpl getCommonDao() {
        return commonDao.get();
    }

    public String getSessId(){
        if(TextUtils.isEmpty(sessId)){
//            sessId = PreUtils.getString(this, cn.com.mangopi.android.Constants.SESS_ID, "");
            sessId = "5a07eeda-5485-441c-85d1-c29058559169";
//            sessId = "46e15e6c-2ff3-4ee9-a596-db0472a32bb2";
//            sessId = "c37d1b12-8521-43e6-b8ee-62b9bc41f979";
        }
        return sessId;
    }

    public MemberBean getMember(){
        if(member != null){
            return member;
        }
        if(!TextUtils.isEmpty(getSessId())){
            CommonDaoImpl commonDao = getCommonDao();
            List<CommonBean> members = commonDao.findByColumn(CommonBean.DATA_TYPE, MemberBean.DATA_TYPE + "_" + getSessId());
//            List<CommonBean> members = commonDao.findByColumn(CommonBean.DATA_TYPE, MemberBean.DATA_TYPE + "_" + PreUtils.getString(this, cn.com.mangopi.android.Constants.SESS_ID, ""));
            if(members!= null && members.size() > 0){
                return (MemberBean) members.get(0).getData();
            } else {
                return null;
            }
        }
        return null;
    }

    public void loginOut(){
        CommonDaoImpl commonDao = getCommonDao();
        List<CommonBean> members = commonDao.findByColumn(CommonBean.DATA_TYPE, MemberBean.DATA_TYPE + "_" + getSessId());
        commonDao.deleteList(members);

        member = null;
        PreUtils.putString(this, cn.com.mangopi.android.Constants.SESS_ID, "");
    }

    public void saveMember(MemberBean member, String sessId){
        this.member = member;
        this.sessId = sessId;

        CommonDaoImpl commonDao = getCommonDao();
        List<CommonBean> members = commonDao.findByColumn(CommonBean.DATA_TYPE, MemberBean.DATA_TYPE + "_" + getSessId());
        if(members!= null && members.size() > 0){
            members.get(0).setData(member);
            commonDao.update(members.get(0));
        } else {
            CommonBean bean = new CommonBean();
            bean.setData(member);
            bean.setData_type(MemberBean.DATA_TYPE + "_" + sessId);
            commonDao.save(bean);
        }

        PreUtils.putString(this, cn.com.mangopi.android.Constants.SESS_ID, sessId);
    }

    public void addActivity(Activity activity){
        activities.get().add(activity);
    }

    public void removeActivity(Activity activity){
        activities.get().remove(activity);
    }

    public Activity getTopActivity(){
        return activities.get().get(activities.get().size() - 1);
    }

    public void exit(){
        for (Activity activity : activities.get()){
            if(activity != null && !activity.isFinishing()){
                activity.finish();
            }
            System.exit(0);
        }
    }

    public void finishBesides(Activity besides){
        for (Activity activity : activities.get()) {
            if (activity != null && !activity.isFinishing() && besides != activity) {
                activity.finish();
            }
        }
    }
}
