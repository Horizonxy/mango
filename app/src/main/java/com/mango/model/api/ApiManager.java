package com.mango.model.api;

import com.mango.Application;
import com.mango.model.bean.RestResult;

import rx.Observable;

public class ApiManager {


    public static Observable<RestResult<Object>> getRegistVerifyCode(String mobile){
        return Application.application.getApiService().getRegistVerifyCode(mobile);
    }

}
