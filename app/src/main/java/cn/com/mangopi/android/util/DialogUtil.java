package cn.com.mangopi.android.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.com.mangopi.android.R;

public class DialogUtil {

    public static void createUpdateRoleAlertDialog(Context context, String title, String message, String btnMsg){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert_update_role, null ,false);
        dialog.setContentView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvOk.setText(btnMsg);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void createUpdateRoleChoosseDialog(Context context, String title, String message, String btnOkMsg, String btnCancelMsg,final OnChooseDialogListener onChooseDialogListener){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_update_role, null ,false);
        dialog.setContentView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvOk.setText(btnOkMsg);
        tvCancel.setText(btnCancelMsg);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChooseDialogListener != null){
                    onChooseDialogListener.onChoose();
                }
                dialog.dismiss();
            }
        });

    }

    public static void createAlertDialog(Context context, String message, String btnMsg){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null ,false);
        dialog.setContentView(view);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvMessage.setText(message);
        tvOk.setText(btnMsg);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public static void createChoosseDialog(Context context, String title, String message, String btnOkMsg, String btnCancelMsg,final OnChooseDialogListener onChooseDialogListener){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose, null ,false);
        dialog.setContentView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvOk.setText(btnOkMsg);
        tvCancel.setText(btnCancelMsg);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChooseDialogListener != null){
                    onChooseDialogListener.onChoose();
                }
                dialog.dismiss();
            }
        });

    }

    public interface OnChooseDialogListener {
        void onChoose();
    }

}
