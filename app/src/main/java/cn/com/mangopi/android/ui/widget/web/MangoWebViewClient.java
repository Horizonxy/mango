package cn.com.mangopi.android.ui.widget.web;

import android.Manifest;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.PermissionUtils;

public class MangoWebViewClient extends WebViewClient {

    private static String IMG_CACHE = FileUtils.getEnvPath(Application.application, true, Constants.IMG_WEB_CACHE_DIR);

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (isImage(url) && PermissionUtils.checkPermissions(view.getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            File image = FileUtils.findFile(IMG_CACHE, fileName.length() > 100 ? fileName.substring(fileName.length() - 100) : fileName);
            if (image != null) {
                try {
                    return new WebResourceResponse("image/png", "UTF-8", new FileInputStream(image));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                InputStream is = downloadImage2Cache(url);
                if(is != null){
                    return new WebResourceResponse("image/png", "UTF-8", is);
                }
            }
        }

        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    private InputStream downloadImage2Cache(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                final InputStream is = conn.getInputStream();
                return new ImageInputStream(url, is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isImage(String str) {
        if(TextUtils.isEmpty(str)){
            return false;
        }
        String suffix = str.substring(str.lastIndexOf(".") + 1);
        return (null != suffix) && (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpeg") ||
                suffix.equalsIgnoreCase("bmp") || suffix.equalsIgnoreCase("gif"));
    }

    class ImageInputStream extends BufferedInputStream {

        private FileOutputStream fos;
        private int len;

        public ImageInputStream(String url, InputStream is) {
            super(is);
            this.len = 0;
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            File file = FileUtils.findFile(IMG_CACHE, fileName.length() > 100 ? fileName.substring(fileName.length() - 100) : fileName);
            if(file == null){
                file = new File(IMG_CACHE, fileName.length() > 100 ? fileName.substring(fileName.length() - 100) : fileName);
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public synchronized int read(byte[] buffer, int byteOffset, int byteCount)
                throws IOException {
            len = super.read(buffer, byteOffset, byteCount);

            if (len == -1) {
                fos.flush();
                fos.close();
            } else {
                fos.write(buffer, 0, len);
            }

            return len;
        }

    }
}
