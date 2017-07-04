package com.mango.ui.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.mango.R;

public class LoadingDialog  extends ProgressDialog {

    private String message;
    private Context context;

    public LoadingDialog(Context context, String message) {
        super(context, R.style.Loading_Dialog);
        this.context = context;
        this.message = message;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_view);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        TextView messageText = (TextView) this.findViewById(R.id.message);
        messageText.setText(message);
    }

    public void setMessage(String message){
        this.message = message;
        TextView messageText = (TextView) this.findViewById(R.id.message);
        messageText.setText(message);
    }

}
