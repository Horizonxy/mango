package cn.com.mangopi.android.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import rx.functions.Action1;


public class PhoneUtils {
	
	/**
	 * 获取屏幕宽度
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity){
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}
	
	/**
	 * 获取density
	 * 
	 * @param activity
	 * @return
	 */
	public static float getScaledDensity(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.scaledDensity;
	}

	/**
	 * 获取dpi
	 * 
	 * @param activity
	 * @return
	 */
	public static float getDensityDpi(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.densityDpi;
	}
	
	/**
	 * 获取屏幕高度
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Activity activity){
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	} 
	
	/**
	 * 获取手机的IMEI码
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context){
		if(MangoUtils.checkPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE})){
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getDeviceId();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取手机序列号
	 * @param context
	 * @return
	 */
	public static String getSerial(Context context) {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

	/**
	 * 获取手机的IMSI码
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context){
		if(MangoUtils.checkPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE})) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getSubscriberId();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取CPU名字
	 * @return
	 */
	public static String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 总内存大小信息
	 */
	public static void getTotalMemory() {  
        String str1 = "/proc/meminfo";  
        String str2="";  
        try {  
            FileReader fr = new FileReader(str1);  
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);  
            while ((str2 = localBufferedReader.readLine()) != null) {  
                LogUtils.i(str2);
            }  
        } catch (IOException e) {  
        }  
    }  	
	
	/**
	 * 剩余内存(ram)大小
	 * @param context
	 * @return
	 */
	public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();  
        am.getMemoryInfo(mi);  
        return mi.availMem;  
    }  
	
	/**
	 * 根据wifi获取mac地址
	 * @param context
	 * @return
	 */
	public static String getMacFromWifi(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress(); 
    }
	
	/**
	 * 获取sdk 号
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getAndroidOSVersion()  
    {  
         int osVersion;  
         try  
         {  
            osVersion = Integer.valueOf(Build.VERSION.SDK);
         }  
         catch (NumberFormatException e)  
         {  
            osVersion = 0;  
         }  
           
         return osVersion;  
   } 
	
	/**
	 * 获取手机号
	 * @param context
	 * @return
	 */
	public static String getPhoneNo(Context context){
		if(MangoUtils.checkPermissions(context, new String[]{Manifest.permission.READ_SMS})) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getLine1Number();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取手机型号
	 * @return
	 */
	public static String getPhoneModel(){
		return Build.MODEL;
	}
	
	/**
	 * 获取手机系统版本
	 * @return
	 */
	public static String getSystemVersion() {  
        return Build.VERSION.RELEASE;
    } 
	
	/**
	 * 判断是否是3G网络
	 * @param context
	 * @return
	 */
	public static boolean is3rd(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {   
            return true;   
        }   
        return false;   
    }  
	
	/**
	 * 判断是否是wifi网络
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {   
            return true;   
        }   
        return false;   
    }
	
	/**
	 * 获取手机网络类型
	 * @param context
	 * @return
	 */
	public static String getConnTypeName(Context context) { 
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo networkInfo = cm.getActiveNetworkInfo(); 
		if(networkInfo != null) { 
			return networkInfo.getTypeName(); 
		} 
		return null;
	} 

	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
        	//如果仅仅是用来判断网络连接 ,则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            @SuppressWarnings("deprecation")
			NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    } 
	
	/**
	 * 返回当前程序版本号 
	 * @param context
	 * @return
	 */
	public static Integer getAppVersionNo(Context context) {  
		Integer versionNo = 0;  
	    try {   
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionNo = pi.versionCode;  
	    } catch (Exception e) {  
			e.printStackTrace();
	    }  
	    return versionNo;  
	}  
	
	/**
	 * 返回当前程序版本名 
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {  
	    String versionName = "";  
	    try {   
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionName = pi.versionName;  
	        if (versionName == null || versionName.length() <= 0) {  
	            return "";  
	        }  
	    } catch (Exception e) {  
			e.printStackTrace();
	    }  
	    return versionName;  
	}  
	
	/**
	 * 获取屏幕分辨率
	 * @param context
	 * @return int[]{width,height}
	 */
	@SuppressWarnings("deprecation")
	public static int[] getResolution(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display screen = wm.getDefaultDisplay();
		return new int[] { screen.getWidth(), screen.getHeight() };
	}

	public static float getDensity(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		return dm.density;
	}

}
