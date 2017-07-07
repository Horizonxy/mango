package com.mango.model.api;

import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.MemberBean;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @POST("outer/router?quick_login")
    Observable<RestResult<RegistBean>> getLoginVerifyCode(
            @Query("mobile") String mobile
    );

    @POST("outer/router?quick_login")
    Observable<RestResult<RegistBean>> quickLogin(
            @Query("mobile") String mobile,
            @Query("sms_code") String smsCode,
            @Query("lst_sessid") String sessId
    );

    @POST("outer/router?update_member")
    Observable<RestResult<Object>> updateMember(
            @Query("nick_name") String nickName,
            @Query("gender") int gender,
            @Query("lst_sessid") String sessId
    );

    @GET("outer/router?member_get")
    Observable<RestResult<MemberBean>> getMember(@Query("id") long id);

    @GET("outer/router?setting_list")
    Observable<RestResult<List<AdvertBean>>> getAdvert(@Query("user_identity") String user_identity, @Query("position") String position);
}
