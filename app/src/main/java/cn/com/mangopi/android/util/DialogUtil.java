package cn.com.mangopi.android.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MessageDetailBean;

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

    public static void createInputDialog(Context context, String title, String btnOkMsg, String btnCancelMsg, String hint, final OnInputDialogListener onInputDialogListener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null ,false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        dialog.show();
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        EditText etContent = (EditText) view.findViewById(R.id.et_content);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        etContent.setHint(hint);
        etContent.requestFocus();
        tvTitle.setText(title);
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
                if(onInputDialogListener != null){
                    onInputDialogListener.onInput(etContent.getText().toString());
                }
                dialog.dismiss();
            }
        });
    }

    public interface OnChooseDialogListener {
        void onChoose();
    }

    public interface OnInputDialogListener {
        void onInput(String text);
    }

    public static void createDatePickerDialog(Context context, String title, View contentView, String btnOkMsg, String btnCancelMsg, final OnChooseDialogListener onChooseDialogListener){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_date_select, null ,false);
        dialog.setContentView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        FrameLayout layoutContent = (FrameLayout) view.findViewById(R.id.layout_content);
        layoutContent.addView(contentView);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvTitle.setText(title);
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

    public static void createProjectJoinMsgDialog(Context context, MessageDetailBean messageDetail, OnProjectJoinMsgListener joinMsgListener){
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_project_join_message, null ,false);
        dialog.setContentView(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        TextView tvEntityName = (TextView) view.findViewById(R.id.tv_entity_name);
        tvEntityName.setText(messageDetail.getEntity_name());
        tvTitle.setText(messageDetail.getTitle());
        tvMessage.setText(messageDetail.getResult());
        if(messageDetail.getState() != null && messageDetail.getState().intValue() != -2) {
            view.findViewById(R.id.layout_func).setVisibility(View.VISIBLE);
            Button btnAgree = (Button) view.findViewById(R.id.btn_agree);
            Button btnRefuse = (Button) view.findViewById(R.id.btn_refuse);
            Button btnThinkAbout = (Button) view.findViewById(R.id.btn_think_about);

            btnAgree.setOnClickListener((v) -> {
                if(joinMsgListener != null){
                    joinMsgListener.onAgree();
                }
                dialog.dismiss();
            });
            btnRefuse.setOnClickListener((v) -> {
                if(joinMsgListener != null){
                    joinMsgListener.onRefuse();
                }
                dialog.dismiss();
            });
            btnThinkAbout.setOnClickListener((v) -> dialog.dismiss());
        } else {
            view.findViewById(R.id.layout_ok).setVisibility(View.VISIBLE);
            Button btnOk = (Button) view.findViewById(R.id.btn_ok);
            btnOk.setOnClickListener((v) -> dialog.dismiss());
        }
    }

    public interface OnProjectJoinMsgListener {
        void onAgree();
        void onRefuse();
    }
}
