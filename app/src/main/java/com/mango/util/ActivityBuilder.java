package com.mango.util;

import android.app.Activity;
import android.content.Intent;

import com.mango.Constants;
import com.mango.R;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.MemberCardBean;
import com.mango.ui.activity.AddBlankCardActivity;
import com.mango.ui.activity.BulletinDetailActivity;
import com.mango.ui.activity.CalssListActivity;
import com.mango.ui.activity.CourseDetailActivity;
import com.mango.ui.activity.InteractAreaActivity;
import com.mango.ui.activity.LoginActivity;
import com.mango.ui.activity.MainActivity;
import com.mango.ui.activity.MemberCardListActivity;
import com.mango.ui.activity.MyClassesActivity;
import com.mango.ui.activity.PlaceOrderActivity;
import com.mango.ui.activity.PublishDynamicsActivity;
import com.mango.ui.activity.SetNickNameActivity;
import com.mango.ui.activity.TutorClassCategoryActivity;
import com.mango.ui.activity.TutorDetailActivity;

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

    public static void startTutorClassCategoryActivity(Activity activity, List<CourseClassifyBean> classifyList){
        Intent intent = new Intent(activity, TutorClassCategoryActivity.class);
        intent.putExtra(Constants.BUNDLE_CLASSIFY_LIST, (Serializable) classifyList);
        activity.startActivity(intent);
    }

    public static void startMyClassesActivity(Activity activity){
        activity.startActivity(new Intent(activity, MyClassesActivity.class));
    }

    public static void startCalssListActivity(Activity activity, CourseClassifyBean classify){
        Intent intent = new Intent(activity, CalssListActivity.class);
        intent.putExtra(Constants.BUNDLE_CLASSIFY, classify);
        activity.startActivity(intent);
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

    public static void startTutorDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, TutorDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }

    public static void startWebViewActivity(Activity activity, BulletinBean bulletin){
        Intent intent = new Intent(activity, BulletinDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_BULLETIN, bulletin);
        activity.startActivity(intent);
    }

    public static void startCourseDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, CourseDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }

    public static void startPlaceOrderActivity(Activity activity, long id){
        Intent intent = new Intent(activity, PlaceOrderActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }
}
