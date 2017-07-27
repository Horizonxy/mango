package cn.com.mangopi.android.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Looper;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.com.mangopi.android.BuildConfig;
import cn.com.mangopi.android.R;

public class AppUtils {

    public static void initCarsh(Context context){
        if(!BuildConfig.DEBUG) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(context);
        }
    }

    public static void showToast(final Context context, final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            MyToast.showToast(context, text);
        } else {
            // 异步线程不处理
        }
    }

    public static void showToast(final Context context, final int resid) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            MyToast.showToast(context, resid);
        } else {
            // 异步线程不处理
        }
    }

    private static class MyToast {
        private static Toast mToast;
        public static void showToast(Context context, String text) {
            showToast(context, text, Toast.LENGTH_SHORT);
        }

        public static void showToast(Context context, int resid) {
            showToast(context, resid, Toast.LENGTH_SHORT);
        }

        public static void showToast(Context context, int resid,
                                     Object... formatArgs) {
            showToast(context, resid, Toast.LENGTH_SHORT, formatArgs);
        }

        public static void showToast(Context context, int resid, int duration) {
            try {
                if(mToast==null){
                    mToast = new Toast(context.getApplicationContext());
                    mToast.setDuration(duration);
                }
                if (resid == R.string.noconnectionremind || resid == R.string.noconnection) {
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                } else if(resid==R.string.live_network_error){
                    mToast.setGravity(Gravity.TOP, 0, 0);
                }else {
                    mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0,
                            context.getResources().getDimensionPixelSize(R.dimen.dp_64));
                }
                mToast.setView(customView(context.getApplicationContext(), resid));

                mToast.show();
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }

        public static void showToast(Context context, int resid, int duration,
                                     Object... formatArgs) {
            try {
                if (mToast == null) {
                    mToast = new Toast(context.getApplicationContext());
                    mToast.setDuration(duration);
                }
                if (resid == R.string.noconnectionremind || resid == R.string.noconnection) {
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                } else {
                    mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0,
                            context.getResources().getDimensionPixelSize(R.dimen.dp_64));
                }
                mToast.setView(customView(context.getApplicationContext(),
                        context.getString(resid, formatArgs)));
                mToast.show();
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }

        public static void showToast(Context context, String text, int duration) {
            try {
                if (mToast == null) {
                    mToast = new Toast(context.getApplicationContext());
                }
                mToast.setDuration(duration);
                mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0,
                        context.getResources().getDimensionPixelSize(R.dimen.dp_64));
                mToast.setView(customView(context.getApplicationContext(), text));
                mToast.show();
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }

        public static void showToast(Context context, String text, int duration, int gravity) {
            try {
                if (mToast == null) {
                    mToast = new Toast(context.getApplicationContext());
                }
                mToast.setDuration(duration);
                mToast.setGravity(gravity, 0,
                        context.getResources().getDimensionPixelSize(R.dimen.dp_64));
                mToast.setView(customView(context.getApplicationContext(), text));
                mToast.show();
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }

        public static void showToast(Context context, int resId, int duration, int gravity) {
            try {
                if (mToast == null) {
                    mToast = new Toast(context.getApplicationContext());
                }
                mToast.setDuration(duration);
                mToast.setGravity(gravity, 0,
                        context.getResources().getDimensionPixelSize(R.dimen.dp_64));
                mToast.setView(customView(context.getApplicationContext(), resId));
                mToast.show();
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }
    }

    private static View customView(Context context, int resId) {
        View v = null;
        if (resId == R.string.noconnectionremind || resId == R.string.noconnection) {
            v = findNetToastView(context);
        } else {
            v = findToastView(context);
        }
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(resId);
        return v;
    }

    private static View findToastView(Context context) {
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.transient_notification, null);
        return v;
    }

    private static View findNetToastView(Context context) {
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.layout_toast_tip, null);
        return v;
    }

    private static View customView(Context context, String text) {
        View v = findToastView(context);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(text);
        return v;
    }


    /**
     * 从APK的压缩包里面, 读取出对应的渠道包名字, 而不是从AndroidManifest里面读,
     * 目的是为了更快打包渠道包而采用的动态设置渠道包
     **/
    public static String getChannel(Context context) {
       String channel = null;
        final String start_flag = "META-INF/channel_";
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(start_flag)) {
                    channel = entryName.replace(start_flag, "");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (channel == null || channel.length() <= 0) {
            channel = "develop";//读不到渠道号就默认是官方渠道
        }
        return channel;
    }

    public static int getMaxLength(EditText et) {
        int length = 0;
        try {
            InputFilter[] inputFilters = et.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 判断程序是否在前台运行
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}
