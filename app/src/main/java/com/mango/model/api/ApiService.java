package com.mango.model.api;

import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @POST("outer/router?register")
    Observable<RestResult<RegistBean>> getRegistVerifyCode(
            @Query("mobile") String mobile
    );

    @POST("outer/router?register")
    Observable<RestResult<RegistBean>> login(
            @Query("mobile") String mobile,
            @Query("sms_code") String smsCode,
            @Query("lst_sessid") String sessId
    );
}
