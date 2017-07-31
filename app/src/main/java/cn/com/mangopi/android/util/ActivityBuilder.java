package cn.com.mangopi.android.util;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.ui.activity.AddBlankCardActivity;
import cn.com.mangopi.android.ui.activity.BulletinDetailActivity;
import cn.com.mangopi.android.ui.activity.CalssListActivity;
import cn.com.mangopi.android.ui.activity.CourseDetailActivity;
import cn.com.mangopi.android.ui.activity.InteractAreaActivity;
import cn.com.mangopi.android.ui.activity.LoginActivity;
import cn.com.mangopi.android.ui.activity.MainActivity;
import cn.com.mangopi.android.ui.activity.MemberCardListActivity;
import cn.com.mangopi.android.ui.activity.MyClassesActivity;
import cn.com.mangopi.android.ui.activity.OrderDetailActivity;
import cn.com.mangopi.android.ui.activity.PlaceOrderActivity;
import cn.com.mangopi.android.ui.activity.PublishDynamicsActivity;
import cn.com.mangopi.android.ui.activity.SelectPayActivity;
import cn.com.mangopi.android.ui.activity.SetNickNameActivity;
import cn.com.mangopi.android.ui.activity.SuccessActivity;
import cn.com.mangopi.android.ui.activity.TutorClassCategoryActivity;
import cn.com.mangopi.android.ui.activity.TutorDetailActivity;
import cn.com.mangopi.android.ui.activity.WebViewActivity;

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

    public static void startBulletinDetailActivity(Activity activity, BulletinBean bulletin){
        Intent intent = new Intent(activity, BulletinDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_BULLETIN, bulletin);
        activity.startActivity(intent);
    }

    public static void startCourseDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, CourseDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }

    public static void startPlaceOrderActivity(Activity activity, CourseDetailBean course){
        Intent intent = new Intent(activity, PlaceOrderActivity.class);
        intent.putExtra(Constants.BUNDLE_COURSE_DETAIL, course);
        activity.startActivity(intent);
    }

    public static void startSuccessActivity(Activity activity, String title, String text){
        Intent intent = new Intent(activity, SuccessActivity.class);
        intent.putExtra(Constants.BUNDLE_TITLE, title);
        intent.putExtra(Constants.BUNDLE_TEXT, text);
        activity.startActivity(intent);
    }

    public static void startSelectPayActivity(Activity activity, OrderBean order){
        Intent intent = new Intent(activity, SelectPayActivity.class);
        intent.putExtra(Constants.BUNDLE_ORDER, order);
        activity.startActivity(intent);
    }

    public static void startOrderDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ORDER_ID, id);
        activity.startActivity(intent);
    }

    public static void startWebViewActivity(Activity activity, String url){
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(Constants.BUNDLE_WEBVIEW_URL, url);
        activity.startActivity(intent);
    }
}
