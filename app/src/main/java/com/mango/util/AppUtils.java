package com.mango.util;

import android.content.Context;
import com.mango.BuildConfig;

public class AppUtils {

    public static void initCarsh(Context context){
        if(!BuildConfig.DEBUG) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(context);
        }
    }
}
