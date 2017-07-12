package com.mango.util;

import android.app.Activity;
import android.content.Intent;

import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.MemberCardBean;
import com.mango.ui.activity.AddBlankCardActivity;
import com.mango.ui.activity.CalssListActivity;
import com.mango.ui.activity.InteractAreaActivity;
import com.mango.ui.activity.LoginActivity;
import com.mango.ui.activity.MainActivity;
import com.mango.ui.activity.MemberCardListActivity;
import com.mango.ui.activity.MyClassesActivity;
import com.mango.ui.activity.PublishDynamicsActivity;
import com.mango.ui.activity.SetNickNameActivity;
import com.mango.ui.activity.TeacherClassCategoryActivity;
import com.mango.ui.activity.TeacherDetailActivity;

import java.io.Serializable;
import java.util.List;

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

    public static void startTeacherClassCategoryActivity(Activity activity){
        activity.startActivity(new Intent(activity, TeacherClassCategoryActivity.class));
    }

    public static void startMyClassesActivity(Activity activity){
        activity.startActivity(new Intent(activity, MyClassesActivity.class));
    }

    public static void startCalssListActivity(Activity activity){
        activity.startActivity(new Intent(activity, CalssListActivity.class));
    }

    public static void startCardListActivity(Activity activity, List<MemberCardBean> cardList){
        Intent intent = new Intent(activity, MemberCardListActivity.class);
        if(cardList != null){
            intent.putExtra(Constants.BUNDLE_CARD_LIST, (Serializable) cardList);
        }
        activity.startActivity(intent);
    }

    public static void startAddBlankCardActivity(Activity activity){
        activity.startActivity(new Intent(activity, AddBlankCardActivity.class));
    }

    public static void startTeacherDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, TeacherDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }
}
