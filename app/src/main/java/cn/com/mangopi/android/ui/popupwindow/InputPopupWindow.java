package cn.com.mangopi.android.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.util.MangoUtils;

public class InputPopupWindow extends PopupWindow {

    Activity activity;
    EditText etContent;
    Button btnSend;
    int softKeyBoardHeight;

    public InputPopupWindow(Activity activity, OnInputListener onInputListener){
        super(activity);
        this.activity = activity;
        softKeyBoardHeight = MangoUtils.getDpi(activity);
        RelativeLayout root = (RelativeLayout)activity.getLayoutInflater().inflate(R.layout.popupwindow_input, null);
        etContent = (EditText) root.findViewById(R.id.et_content);
        btnSend = (Button) root.findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onInputListener != null && !TextUtils.isEmpty(etContent.getText())){
                    onInputListener.onInput(etContent.getText().toString());
                    dismiss();
                }
            }
        });
//        SoftKeyBoardListener.setListener(activity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
//            @Override
//            public void keyBoardShow(int height) {
//                softKeyBoardHeight = height;
//            }
//
//            @Override
//            public void keyBoardHide(int height) {
//
//            }
//        });

        setContentView(root);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(android.R.color.transparent));
        this.setBackgroundDrawable(dw);
    }

    public void showWindow(){
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, 0 - softKeyBoardHeight);
        etContent.requestFocus();
        etContent.setFocusable(true);
        toggleSoftInput();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        toggleSoftInput();
    }

    private void toggleSoftInput(){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE );
        if (!imm.isActive(etContent)){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public interface OnInputListener{
        void onInput(String text);
    }
}
