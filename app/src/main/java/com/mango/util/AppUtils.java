package com.mango.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mango.BuildConfig;
import com.mango.R;

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
}
