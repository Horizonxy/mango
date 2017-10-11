package cn.com.mangopi.android.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MaskUtils {

    public static void removeAttchMask(Context context, View layoutView){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(layoutView);
    }

    public static void attachMaskFromRes(Context context, View layoutView, int clickLayoutResId, View.OnClickListener clickListener, int... clickResId){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        final WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        View.OnClickListener removeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    windowManager.removeView(layoutView);
                }catch(Throwable tr){
                    tr.printStackTrace();
                }
            }
        };
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    windowManager.removeView(layoutView);
                }
                return false;
            }
        };
        layoutView.setOnKeyListener(onKeyListener);
        layoutView.setFocusableInTouchMode(true);
        if(clickLayoutResId > 0){
            MangoUtils.setOnClickListener(removeListener, layoutView, clickLayoutResId);
        } else {
            layoutView.setOnClickListener(removeListener);
        }
        MangoUtils.setOnClickListener(clickListener, layoutView, clickResId);
        windowManager.addView(layoutView, layoutParams);
    }

    public static void attachMaskFromRes(Context context, int layoutResId, int clickLayoutResId, View.OnClickListener clickListener, int... clickResId){
        View layoutView = View.inflate(context, layoutResId, null);
        attachMaskFromRes(context, layoutView, clickLayoutResId, clickListener, clickResId);
    }

    public static void attachMaskFromRes(Context context, int layoutResId){
        View layoutView = View.inflate(context, layoutResId, null);
        attachMaskFromRes(context, layoutView, 0, null);
    }

    public static void attachMaskFromRes(Context context, int layoutResId, View.OnClickListener clickListener, int... ids){
        View layoutView = View.inflate(context, layoutResId, null);
        attachMaskFromRes(context, layoutView, 0, clickListener, ids);
    }

    public static void attachMaskFromRes(Context context, int layoutResId, int clickLayoutResId){
        View layoutView = View.inflate(context, layoutResId, null);
        attachMaskFromRes(context, layoutView, clickLayoutResId, null);
    }

}
