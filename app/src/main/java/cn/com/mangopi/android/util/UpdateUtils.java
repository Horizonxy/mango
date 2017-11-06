package cn.com.mangopi.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.BuildConfig;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.AppVisionBean;

public class UpdateUtils {

    private Context context;
    private ProgressBar progress;
    private TextView tvProgress;
    private LinearLayout layoutUpdate;
    private boolean cancel;
    private HttpURLConnection conn;
    private long lastExitTime = 0;

    public UpdateUtils(Context context) {
        this.context = context;
        RichText.initCacheDir(context);
    }

    public void update(AppVisionBean appVision) {
        int visionCode = BuildConfig.VERSION_CODE;
        if (visionCode < appVision.getVersion_code()) {
            String registerId = appVision.getRegister_id();
            if (!TextUtils.isEmpty(registerId)) {
                Installation.updateRegisterId(context, registerId);
            }

            boolean must = visionCode < appVision.getMin_version_code();
            showUpdateInfo(appVision, must);
        }
    }

    private void showUpdateInfo(AppVisionBean appVision, boolean must) {
        final AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).create();
        if (must) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if ((System.currentTimeMillis() - lastExitTime) > 2000) {
                            AppUtils.showToast(context, context.getResources().getString(R.string.exit_message));
                            lastExitTime = System.currentTimeMillis();
                        } else {
                            cancel = true;
                            deleteApk(appVision.getApp_name());
                            Application.application.exit();
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null, false);
        dialog.setContentView(view);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        tvProgress = (TextView) view.findViewById(R.id.tv_progress);
        progress.setProgress(0);
        tvProgress.setText(String.format(context.getString(R.string.progress), 0));
        layoutUpdate = (LinearLayout) view.findViewById(R.id.layout_progress);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvTitle.setText("更新");
        RichText.from(appVision.getContent()).into(tvMessage);
        tvOk.setText("升级");
        if (must) {
            tvCancel.setVisibility(View.GONE);
            view.findViewById(R.id.line_func).setVisibility(View.GONE);
        }
        tvCancel.setText("取消");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel = true;

                deleteApk(appVision.getApp_name());

                dialog.dismiss();

                if(must){
                    Application.application.exit();
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutUpdate.setVisibility(View.VISIBLE);

                tvOk.setVisibility(View.GONE);
                view.findViewById(R.id.line_func).setVisibility(View.GONE);
                tvCancel.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    public void run() {
                        final File file = downloadFile(context, appVision, must);

                        Runnable r = new Runnable() {
                            public void run() {
                                postDownloadFile(context, file,must);
                            }
                        };
                        ((Activity) context).runOnUiThread(r);
                    }
                }).start();
            }
        });
    }

    private void deleteApk(String apkName){
        File path = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) : context.getCacheDir();
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(path, apkName);
        if (file.exists()) {
            file.delete();
        }
    }

    private  File downloadFile(Context context, AppVisionBean appVisionBean, final boolean must) {

        conn = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        File file = null;

        try {
            URL url = new URL("http:"+appVisionBean.getDownload_url());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if(responseCode == 302){
                String location = conn.getHeaderField("Location");
                url = new URL(location);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(10000);
                conn.setRequestMethod("GET");
                conn.connect();
                responseCode = conn.getResponseCode();
            }
            if (responseCode == 200) {
                deleteApk(appVisionBean.getApp_name());

                outputStream = new FileOutputStream(file);
                inputStream = conn.getInputStream();
                byte[] buff = new byte[1024];
                int cbio, totalRead = 0;
                int contentLength = conn.getContentLength();

                Bundle bundle = new Bundle();
                bundle.putInt("total", contentLength);
                UpdateHandler handler = new UpdateHandler((Activity)context);
                int updateMsg = 0;
                while ((cbio = inputStream.read(buff)) != -1) {
                    if(cancel){
                        return null;
                    }
                    totalRead += cbio;
                    updateMsg += cbio;
                    outputStream.write(buff, 0, cbio);

                    if(updateMsg * 100 / contentLength >= 3){
                        Message msg = new Message();
                        msg.what = 0;
                        bundle.putInt("current", totalRead);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        updateMsg = 0;
                    }
                }
                handler.sendEmptyMessage(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(conn != null) {
                conn.disconnect();
            }
        }
        return file;
    }

    class UpdateHandler extends Handler {
        private Activity context;
        private int dp39;

        @SuppressLint("HandlerLeak")
        public UpdateHandler(Activity context) {
            super(context.getMainLooper());
            this.context = context;
            this.dp39 = DisplayUtils.dip2px(context, 39);
        }

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();
                    int len = bundle.getInt("current")*100 / bundle.getInt("total");
                    if(progress != null){
                        progress.setProgress(len);
                    }

                    int matchWitdth = progress.getWidth();
                    int leftMargin = (int) (matchWitdth / 100F * len);
                    LinearLayout.LayoutParams tvProgressParams = (LinearLayout.LayoutParams) tvProgress.getLayoutParams();
                    tvProgressParams.leftMargin = leftMargin + dp39;
                    tvProgress.setLayoutParams(tvProgressParams);
                    tvProgress.setText(String.format(context.getString(R.string.progress), len));
                    break;
                case 1:
                    if(progress != null){
                        progress.setProgress(100);
                    }
                    break;
            }
        }
    }

    private void postDownloadFile(Context context, File result, boolean must) {
        if (result == null)
            return;
        String[] args = { "chmod", "604", result.getAbsolutePath() };
        exec(args);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", result);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    private String exec(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.redirectErrorStream(true);
        Process process = null;
        InputStream stdout = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            process = processBuilder.start();
            stdout = process.getInputStream();
            int cbio;
            while ((cbio = stdout.read()) != -1) {
                byteArrayOutputStream.write(cbio);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stdout != null) {
                    stdout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return byteArrayOutputStream.toString();
    }
}
