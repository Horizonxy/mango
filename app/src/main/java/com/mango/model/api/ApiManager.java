package com.mango.model.api;

import com.mango.Application;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

import rx.Observable;

public class ApiManager {


    public static Observable<RestResult<RegistBean>> getRegistVerifyCode(String mobile){
        return Application.application.getApiService().getRegistVerifyCode(mobile);
    }

    public static  Observable<RestResult<RegistBean>> login(String mobile, String smsCode, String sessId){
        return Application.application.getApiService().login(mobile, smsCode, sessId);
    }

}
