package com.mango.model.api;

import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @POST("outer/router?register")
    Observable<RestResult<Object>> getRegistVerifyCode(@Query("mobile") String mobile);

}
