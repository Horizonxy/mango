package com.mango.model.api;

import com.mango.Application;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.MemberBean;
import com.mango.model.bean.MemberCardBean;
import com.mango.model.bean.MemberWalletBean;
import com.mango.model.bean.OrderBean;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;
import com.mango.model.bean.TrendBean;

import java.util.List;
import java.util.Map;

import rx.Observable;

public class ApiManager {


    public static Observable<RestResult<RegistBean>> getLoginVerifyCode(String mobile){
        return Application.application.getApiService().getLoginVerifyCode(mobile);
    }

    public static  Observable<RestResult<RegistBean>> quickLogin(String mobile, String smsCode, String sessId){
        return Application.application.getApiService().quickLogin(mobile, smsCode, sessId);
    }

    public static  Observable<RestResult<Object>> updateMember(String nickName, int gender, String sessId){
        return Application.application.getApiService().updateMember(nickName, gender, sessId);
    }

    public static  Observable<RestResult<MemberBean>> getMember(long id){
        return Application.application.getApiService().getMember(id);
    }

    public static  Observable<RestResult<List<AdvertBean>>> getAdvert(String userIdentity, String position){
        return Application.application.getApiService().getAdvert(userIdentity, position);
    }

    public static  Observable<RestResult<List<BulletinBean>>> getBulletinList(int pageNo, int pageSize){
        return Application.application.getApiService().getBulletinList(pageNo, pageSize);
    }

    public static  Observable<RestResult<List<CourseClassifyBean>>> getClassifyList(Map<String, Long> map){
        return Application.application.getApiService().getClassifyList(map);
    }

    public static  Observable<RestResult<List<TrendBean>>> getTrendList(Map<String, Object> map){
        return Application.application.getApiService().getTrendList(map);
    }

    public static  Observable<RestResult<Object>> praise(long entityId, int entityTypeId){
        return Application.application.getApiService().praise(Application.application.getSessId(), entityId, entityTypeId);
    }

    public static  Observable<RestResult<Object>> addTrend(String content, List<String> pics){
        return Application.application.getApiService().addTrend(Application.application.getSessId(), content, pics);
    }

    public static  Observable<RestResult<MemberWalletBean>> getWallet(){
        return Application.application.getApiService().getWallet(Application.application.getSessId());
    }

    public static  Observable<RestResult<List<MemberCardBean>>> getCardList(){
        return Application.application.getApiService().getCardList(Application.application.getSessId());
    }

    public static  Observable<RestResult<List<OrderBean>>> getOrderList(Map<String, Object> map){
        return Application.application.getApiService().getOrderList(map);
    }

    public static  Observable<RestResult<Object>> addBlankCard(String blankName, String cardNo){
        return Application.application.getApiService().addBlankCard(Application.application.getSessId(), blankName, cardNo);
    }

    public static  Observable<RestResult<List<CourseBean>>> getCourseList(Map<String, Object> map){
        return Application.application.getApiService().getCourseList(map);
    }
}
