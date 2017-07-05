package com.mango.util;

import android.app.Activity;
import android.content.Intent;

import com.mango.R;
import com.mango.ui.activity.InteractAreaActivity;
import com.mango.ui.activity.LoginActivity;
import com.mango.ui.activity.MainActivity;
import com.mango.ui.activity.PublishDynamicsActivity;
import com.mango.ui.activity.SetNickNameActivity;

/**
 * @author 蒋先明
 * @date 2017/7/4
 */
public class ActivityBuilder {

    public static void defaultTransition(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public static void startLoginActivity(Activity activity){
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    public static void startMainActivity(Activity activity){
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    public static void startSetNickNameActivity(Activity activity){
        activity.startActivity(new Intent(activity, SetNickNameActivity.class));
    }

    public static void startPublishDynamicsActivity(Activity activity){
        activity.startActivity(new Intent(activity, PublishDynamicsActivity.class));
    }

    public static void startInteractAreaActivity(Activity activity){
        activity.startActivity(new Intent(activity, InteractAreaActivity.class));
    }
}
