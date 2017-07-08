package com.mango.model.api;

import com.mango.Application;
import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.MemberBean;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

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
}
