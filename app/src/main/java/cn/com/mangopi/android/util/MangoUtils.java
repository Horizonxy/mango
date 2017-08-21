package cn.com.mangopi.android.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.MemberBean;

/**
 * @author 蒋先明
 * @date 2017/7/4
 */

public class MangoUtils {

    public static void saveData(Context context, String name, Object obj){
        FileOutputStream stream;
        try {
            stream = context.openFileOutput(name, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  Object getData(Context context, String name){
        FileInputStream stream;
        try {
            stream = context.openFileInput(name);
            ObjectInputStream ois = new ObjectInputStream(stream);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void backgroundAlpha(Context context, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public static boolean isValidDate(String str) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static void synCookies(Context context, String domain, String... cookie) {
        if(cookie.length == 0 || cookie.length%2 != 0) return;
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        CookieSyncManager.getInstance().startSync();
        cookieManager.setAcceptCookie(true);
        for (int i = 0; i < cookie.length; i+=2) {
            String c = cookie[i]+"=" + cookie[i+1];
            LogUtils.i("cookie: "+c);
            cookieManager.setCookie(domain, c);
        }
        cookieSyncManager.sync();
    }

    public static void clearCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        CookieSyncManager.getInstance().startSync();
        cookieManager.removeSessionCookie();
        CookieSyncManager.getInstance().sync();
    }

    public static String makeHtml(String...strings){
        if (strings == null || strings.equals("") || strings.length <= 0) {
            return "";
        }

        StringBuffer bf = new StringBuffer();
        bf.append("<!DOCTYPE HTML>");
        bf.append("<html>");
        bf.append("<head>");
        bf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        bf.append("<meta content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=no\" name=\"viewport\"/>");
        bf.append("</head>");
        bf.append("<body>");
        bf.append("<head>");
        for (String s:strings) {
            if (s == null) {
                continue;
            }
            //特殊处理富文本中有图片，没有width属性，图片超宽的问题
//			int i1 = s.indexOf("width=");
            int i2 = s.indexOf("<img");
            if (i2 != -1 ) {
                s = s.replaceAll("<img", "<img width=\"100%\"");
                if(s.indexOf("src=\"//") != -1){
                    s = s.replaceAll("src=\"//", "src=\"http://");
                }
            }
            bf.append(" ");
            bf.append(s.trim());
        }
        bf.append("</body>");
        bf.append("</html>");
        return bf.toString();
    }

    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static String priceFormat(String str){
        if(str == null){
            return "";
        }
        return decimalFormat.format(Double.parseDouble(str));
    }


    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    public static boolean getUninatllApkInfo(Context context,String filePath) {
        boolean result = false;
        try {
            PackageManager pm = context.getPackageManager();
            Logger.d("archiveFilePath", filePath);
            PackageInfo info = pm.getPackageArchiveInfo(filePath,PackageManager.GET_ACTIVITIES);
            if (info != null) {
                result = true;//完整
            }
        } catch (Exception e) {
            result = false;//不完整
        }
        return result;
    }

    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;

        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                LogUtils.i(info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="+info.baseActivity.getPackageName());
                break;
            }
        }
        return isAppRunning;
    }


    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    /**
     * 距今时间
     * @author 蒋先明
     * @param date
     * @return
     */
    public static String toToday(Date date) {
        if(date == null){
            return "";
        }
        long time = date.getTime() / 1000;
        long now = (new Date().getTime()) / 1000;
        long ago = now - time;
        if(ago < 0){
            ago = 0;
        }
        if(ago <= ONE_MINUTE){
            return ago + "秒前";
        }
        else if (ago <= ONE_HOUR)
            return ago / ONE_MINUTE + "分钟前";
        else if (ago <= ONE_DAY)
            return ago / ONE_HOUR + "小时前";
        else if (ago <= ONE_DAY * 2)
            return "1天前";
        else if (ago <= ONE_DAY * 3) {
            return "两天前";
        } else {
            return DateUtils.dateToString(date, "yyyy.MM.dd");
        }
        /*if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            return day + "天前";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            return month + "个月前";
        } else {
            long year = ago / ONE_YEAR;
            return year + "年前";
        }
*/
    }


    /**
     * Description:显示前3位和后4位。如13812345678转换后为：138*****5678
     *
     * @param num 需要隐藏的手机号
     * @return String
     */
    public static String hideMobileNO(String num) {
        if (!Inputvalidator.checkPhone(num)) {
            return num;
        }
        return num.replaceAll("(.{3}).*(.{4})", "$1****$2");
    }

    /**
     * 计算宽高
     *
     * @param screenWidth 理想的屏幕宽度
     * @param senseW 理想的view宽度
     * @param scaleW 宽比重
     * @param scaleH 高比重
     * @return
     */
    public static int[] getSenseWH(int screenWidth, int senseW, int scaleW, int scaleH){
        int[] wh = new int[2];
        wh[0] = (int) (((float)(DisplayUtils.screenWidth(Application.application) * senseW)) / screenWidth);
        wh[1] = (int) (((float)(wh[0] * scaleH)) / scaleW);
        return wh;
    }

    public static String removeHtmlTag(String content) {
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>");
        Matcher m = p.matcher(content);
        if (m.find()) {
            content = content.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
            content = removeHtmlTag(content);
        }
        return content;
    }

    public static String formatPrice(BigDecimal price){
        return price.setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    public static List<Constants.UserIndentity> getIndentityList(){
        MemberBean member = Application.application.getMember();
        List<Constants.UserIndentity> indentityList = new ArrayList<Constants.UserIndentity>();
        if(member == null){
            return indentityList;
        }
        String[] indentitys = member.getUser_identitys();
        for (int i = 0; indentitys != null && i < indentitys.length; i++){
            indentityList.add(Constants.UserIndentity.get(indentitys[i]));
        }
        return indentityList;
    }

    public static void jumpAdvert(Activity activity, AdvertBean.DetailsBean advertDetail){
        int type = advertDetail.getBind_type();
        if(type == 0){//超链接
            ActivityBuilder.startWebViewActivity(activity, advertDetail.getClick_url());
        } else if(type == 8){//课程
            if(advertDetail.getEntity_id() != null) {
                ActivityBuilder.startCourseDetailActivity(activity, advertDetail.getEntity_id().longValue());
            }
        } else if(type == 6){//工作包

        } else if(type == 10){//内容
            if(advertDetail.getEntity_id() != null) {
                ActivityBuilder.startContentDetailActivity(activity, advertDetail.getEntity_id().longValue());
            }
        }
    }

    public static SmallPicInfo getSmallPicInfo(ImageView imageView, String url){
        int[] screenLocation = new int[2];
        imageView.getLocationOnScreen(screenLocation);
        SmallPicInfo info = new SmallPicInfo(url, screenLocation[0], screenLocation[1], imageView.getWidth(), imageView.getHeight(), 0);
        return info;
    }
}
