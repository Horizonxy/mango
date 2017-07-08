package com.mango.model.api;

import com.mango.model.bean.AdvertBean;
import com.mango.model.bean.BulletinBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.model.bean.MemberBean;
import com.mango.model.bean.RegistBean;
import com.mango.model.bean.RestResult;
import com.mango.model.bean.TrendBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
    Observable<RestResult<MemberBean>> getMember(
            @Query("id") long id
    );

    @GET("outer/router?setting_list")
    Observable<RestResult<List<AdvertBean>>> getAdvert(
            @Query("user_identity") String user_identity,
            @Query("position") String position
    );

    @GET("outer/router?bulletin_list")
    Observable<RestResult<List<BulletinBean>>> getBulletinList(
            @Query("page_no") int pageNo,
            @Query("page_size") int pageSize
    );

    //课程分类列表接口
    //show_top 是否显示在首页(1:是 0：显示所有)
    //parent_id 父分类ID
    @GET("outer/router?classify_list")
    Observable<RestResult<List<CourseClassifyBean>>> getClassifyList(
            @QueryMap Map<String, Long> map
    );


    //动态列表接口
    @GET("outer/router?trend_list")
    Observable<RestResult<List<TrendBean>>> getTrendList(
            @QueryMap Map<String, Object> map
    );
}
