package cn.com.mangopi.android.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.ui.activity.AddBlankCardActivity;
import cn.com.mangopi.android.ui.activity.BulletinDetailActivity;
import cn.com.mangopi.android.ui.activity.CalssListActivity;
import cn.com.mangopi.android.ui.activity.ContentDetailActivity;
import cn.com.mangopi.android.ui.activity.CourseDetailActivity;
import cn.com.mangopi.android.ui.activity.FavListActivity;
import cn.com.mangopi.android.ui.activity.GetCashActivity;
import cn.com.mangopi.android.ui.activity.InputMessageActivity;
import cn.com.mangopi.android.ui.activity.InteractAreaActivity;
import cn.com.mangopi.android.ui.activity.LoginActivity;
import cn.com.mangopi.android.ui.activity.MainActivity;
import cn.com.mangopi.android.ui.activity.MemberCardListActivity;
import cn.com.mangopi.android.ui.activity.MemberTrendActivity;
import cn.com.mangopi.android.ui.activity.MemberWorksActivity;
import cn.com.mangopi.android.ui.activity.MessageListActivity;
import cn.com.mangopi.android.ui.activity.MyClassesActivity;
import cn.com.mangopi.android.ui.activity.OrderDetailActivity;
import cn.com.mangopi.android.ui.activity.OrderScheduleCalendarActivity;
import cn.com.mangopi.android.ui.activity.PayResultActivity;
import cn.com.mangopi.android.ui.activity.PictureDetailActivity;
import cn.com.mangopi.android.ui.activity.PlaceOrderActivity;
import cn.com.mangopi.android.ui.activity.ProfileInfoActivity;
import cn.com.mangopi.android.ui.activity.PublishDynamicsActivity;
import cn.com.mangopi.android.ui.activity.SearchActivity;
import cn.com.mangopi.android.ui.activity.SelectPayActivity;
import cn.com.mangopi.android.ui.activity.SetNickNameActivity;
import cn.com.mangopi.android.ui.activity.SuccessActivity;
import cn.com.mangopi.android.ui.activity.TransDetailActivity;
import cn.com.mangopi.android.ui.activity.TutorClassCategoryActivity;
import cn.com.mangopi.android.ui.activity.TutorDetailActivity;
import cn.com.mangopi.android.ui.activity.UpgradeRoleActivity;
import cn.com.mangopi.android.ui.activity.WebViewActivity;
import cn.com.mangopi.android.ui.activity.WorksProjectDetailActivity;

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
        startCalssListActivity(activity, classify, 0);
    }

    public static void startCalssListActivity(Activity activity, CourseClassifyBean classify, int postion){
        Intent intent = new Intent(activity, CalssListActivity.class);
        intent.putExtra(Constants.BUNDLE_CLASSIFY, classify);
        intent.putExtra(Constants.BUNDLE_POSITION, postion);
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

    public static void startBulletinDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, BulletinDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
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

    public static void startOrderDetailActivity(Activity activity, long id, int relation){
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ORDER_ID, id);
        intent.putExtra(Constants.BUNDLE_ORDER_RELATION, relation);
        activity.startActivity(intent);
    }

    public static void startWebViewActivity(Activity activity, String url){
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(Constants.BUNDLE_WEBVIEW_URL, url);
        activity.startActivity(intent);
    }

    public static void startInputMessageActivity(Activity activity, String title, String right, String type, int limitNum, String content){
        startInputMessageActivity(activity, title, right, type, limitNum, content, false, -1);
    }

    public static void startInputMessageActivity(Activity activity, String title, String right, String type, int limitNum, String content, int inputType){
        startInputMessageActivity(activity, title, right, type, limitNum, content, false, inputType);
    }

    public static void startInputMessageActivity(Activity activity, String title, String right, String type, int limitNum, String content, boolean must, int inputType){
        Intent intent = new Intent(activity, InputMessageActivity.class);
        intent.putExtra(Constants.BUNDLE_TITLE, title);
        intent.putExtra(Constants.BUNDLE_RIGHT_TEXT, right);
        intent.putExtra(Constants.BUNDLE_TYPE, type);
        intent.putExtra(Constants.BUNDLE_LIMIT_NUM, limitNum);
        intent.putExtra(Constants.BUNDLE_CONTENT, content);
        intent.putExtra(Constants.BUNDLE_MUST, must);
        intent.putExtra(Constants.BUNDLE_INPUT_TYPE, inputType);
        activity.startActivity(intent);
    }

    public static void startContentDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, ContentDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }

    public static void startContentDetailActivity(Activity activity, String title, String content){
        Intent intent = new Intent(activity, ContentDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_TITLE, title);
        intent.putExtra(Constants.BUNDLE_CONTENT, content);
        activity.startActivity(intent);
    }

    public static void startMessageListActivity(Activity activity){
        Intent intent = new Intent(activity, MessageListActivity.class);
        activity.startActivity(intent);
    }

    public static void startPayResultActivity(Activity activity){
        Intent intent = new Intent(activity, PayResultActivity.class);
        activity.startActivity(intent);
    }

    public static void startFavListActivity(Activity activity){
        Intent intent = new Intent(activity, FavListActivity.class);
        activity.startActivity(intent);
    }

    public static void startProfileInfoActivity(Activity activity){
        Intent intent = new Intent(activity, ProfileInfoActivity.class);
        activity.startActivity(intent);
    }

    public static void startMemberTrendActivity(Activity activity){
        Intent intent = new Intent(activity, MemberTrendActivity.class);
        activity.startActivity(intent);
    }


    public static void startUpgradeRoleActivityy(Activity activity){
        Intent intent = new Intent(activity, UpgradeRoleActivity.class);
        activity.startActivity(intent);
    }

    public static void startGetCashActivity(Activity activity, BigDecimal availableAmount){
        Intent intent = new Intent(activity, GetCashActivity.class);
        intent.putExtra(Constants.BUNDLE_AMOUNT, availableAmount);
        activity.startActivity(intent);
    }

    public static void startPictureDetailActivity(Activity activity, SmallPicInfo smallPicInfo){
        List<SmallPicInfo> smallPicInfos = new ArrayList<>();
        smallPicInfos.add(smallPicInfo);
        startPictureDetailActivity(activity, smallPicInfos, 0);
    }

    public static void startPictureDetailActivity(Activity activity, List<SmallPicInfo> smallPicInfos, int position){
        Intent intent = new Intent(activity, PictureDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_URL_LIST, (Serializable) smallPicInfos);
        intent.putExtra(Constants.BUNDLE_POSITION, position);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    public static void startOrderScheduleCalendarActivity(Activity activity){
        Intent intent = new Intent(activity, OrderScheduleCalendarActivity.class);
        activity.startActivity(intent);
    }

    public static void startSearchActivity(Activity activity, String searchText){
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(Constants.BUNDLE_TEXT, searchText);
        activity.startActivity(intent);
    }

    public static void startMemberWorksActivity(Activity activity, @NonNull Constants.UserIndentity indentity){
        Intent intent = new Intent(activity, MemberWorksActivity.class);
        if(indentity == Constants.UserIndentity.COMPANY) {
            intent.putExtra(Constants.BUNDLE_WORKS_RELATION, 2);
        } else if(indentity == Constants.UserIndentity.STUDENT){
            intent.putExtra(Constants.BUNDLE_WORKS_RELATION, 1);
        }
        activity.startActivity(intent);
    }

    public static void startWorksProjectDetailActivity(Activity activity, long id){
        Intent intent = new Intent(activity, WorksProjectDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ID, id);
        activity.startActivity(intent);
    }

    public static void startTransDetailActivity(Activity activity, TransListBean trans){
        Intent intent = new Intent(activity, TransDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_DATA, trans);
        activity.startActivity(intent);
    }

    public static void startSystemBrowser(Activity activity, Uri uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}
