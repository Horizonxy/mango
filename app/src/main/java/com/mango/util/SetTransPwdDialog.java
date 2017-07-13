package com.mango.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.mango.R;
import com.mango.ui.widget.PayPwdEditText;

public class SetTransPwdDialog extends Dialog {

    private PayPwdEditText payPwdEditText;

    public SetTransPwdDialog(Context context, onPwdFinishListener onPwdFinishListener) {
        super(context, R.style.SetTransPwdStyleDialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        //透明状态栏
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4全透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.dialog_set_trans_pwd);

        payPwdEditText = (PayPwdEditText) findViewById(R.id.et_pwd);
        payPwdEditText.initStyle(R.drawable.shape_bg_rect_black_border, 6, 0.6F, R.color.color_cccccc, R.color.color_666666, 24);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                if(onPwdFinishListener != null){
                    onPwdFinishListener.onFinish(str);
                }
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(payPwdEditText.getWindowToken(), 0);
                dismiss();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                payPwdEditText.setFocus();
            }
        }, 100);
    }

    public interface onPwdFinishListener {
        void onFinish(String pwd);
    }
}