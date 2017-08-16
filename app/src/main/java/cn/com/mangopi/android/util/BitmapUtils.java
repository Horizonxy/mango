package cn.com.mangopi.android.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 蒋先明
 * @date 2016/8/11
 */
public class BitmapUtils {

    public static File saveBmp2SD(Bitmap bmp, String path, String name){
        if(bmp == null){
            return null;
        }
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(path, name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
