package cn.com.mangopi.android.model.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.ContentDetailBean;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.OrderDetailBean;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.model.bean.TutorBean;
import cn.com.mangopi.android.model.bean.UploadBean;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("outer/router?wx_login_openid")
    Observable<RestResult<RegistBean>> wxLogin(
            @Query("open_id") String openId
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
    Observable<RestResult<ArrayList<AdvertBean>>> getAdvert(
            @Query("user_identity") String user_identity,
            @Query("position") String position
    );

    @GET("outer/router?bulletin_list")
    Observable<RestResult<ArrayList<BulletinBean>>> getBulletinList(
            @Query("page_no") int pageNo,
            @Query("page_size") int pageSize
    );

    //课程分类列表接口
    //show_top 是否显示在首页(1:是 0：显示所有)
    //parent_id 父分类ID
    @GET("outer/router?classify_list")
    Observable<RestResult<ArrayList<CourseClassifyBean>>> getClassifyList(
            @QueryMap Map<String, Long> map
    );


    //动态列表接口
    @GET("outer/router?trend_list")
    Observable<RestResult<List<TrendBean>>> getTrendList(
            @QueryMap Map<String, Object> map
    );

    //点赞接口
    @POST("outer/router?praise_count")
    Observable<RestResult<Object>> praise(
            @Query("lst_sessid") String sessId,
            @Query("entity_id") long entityId,
            @Query("entity_type_id") int entityTypeId
    );

    //5.5.3添加动态接口
    @POST("outer/router?trend_add")
    Observable<RestResult<Object>> addTrend(
            @Query("lst_sessid") String sessId,
            @Query("content") String content,
            @Query("entity_type_id") List<String> pics
    );

    //5.1.17会员钱包详情接口
    @GET("outer/router?member_wallet")
    Observable<RestResult<MemberWalletBean>> getWallet(
            @Query("lst_sessid") String sessId
    );

    //5.1.20会员银行卡列表接口
    @GET("outer/router?card_list")
    Observable<RestResult<List<MemberCardBean>>> getCardList(
            @Query("lst_sessid") String sessId
    );

    //5.3.1订单列表接口
    @POST("outer/router?order_list")
    Observable<RestResult<List<OrderBean>>> getOrderList(
            @QueryMap Map<String, Object> map
    );

    //5.1.18绑定银行卡接口
    @POST("outer/router?add_card")
    Observable<RestResult<Object>> addBlankCard(
            @Query("lst_sessid") String sessId,
            @Query("bank_name") String bank_name,
            @Query("card_no") String card_no
    );

    //导师课程列表接口
    @GET("outer/router?course_list")
    Observable<RestResult<List<CourseBean>>> getCourseList(
            @QueryMap Map<String, Object> map
    );

    //5.1.6检查会员是否允许升级学生身份接口
    @POST("outer/router?check_upgrade_student")
    Observable<RestResult<Object>> checkUpgradeStudent(
            @Query("lst_sessid") String sessId
    );

    //5.1.7 检查会员是否允许升级导师身份接口
    @POST("outer/router?check_upgrade_tutor")
    Observable<RestResult<Object>> checkUpgradeTutor(
            @Query("lst_sessid") String sessId
    );

    //5.1.8检查会员是否允许升级企业身份接口
    @POST("outer/router?check_upgrade_company")
    Observable<RestResult<Object>> checkUpgradeCompany(
            @Query("lst_sessid") String sessId
    );

    //5.1.9检查会员是否允许升级社团身份接口
    @POST("outer/router?check_upgrade_community")
    Observable<RestResult<Object>> checkUpgradeCommunity(
            @Query("lst_sessid") String sessId
    );

    //导师详情接口
    @GET("outer/router?course_tutor")
    Observable<RestResult<TutorBean>> getTutor(
            @Query("id") long id,
            @Query("lst_sessid") String sessId
    );

    //5.2.5课程详情接口
    @GET("outer/router?course_get")
    Observable<RestResult<CourseDetailBean>> getCourse(
            @Query("id") long id,
            @Query("lst_sessid") String sessId
    );

    //5.2.5课程详情接口
    @POST("outer/router?order_add")
    Observable<RestResult<OrderBean>> addOrder(
            @QueryMap Map<String, Object> map
    );

    //添加收藏接口
    @POST("outer/router?fav_add")
    Observable<RestResult<Object>> addFav(
            @Query("lst_sessid") String sessId,
            @Query("entity_id") long entityId,
            @Query("entity_type_id") int entityTypeId
    );

    //删除收藏接口
    @POST("outer/router?fav_del")
    Observable<RestResult<Object>> delFav(
            @Query("lst_sessid") String sessId,
            @Query("entity_id") long entityId,
            @Query("entity_type_id") int entityTypeId
    );

    //5.6.2	 想看接口
    @POST("outer/router?want_count")
    Observable<RestResult<Object>> wantCount(
            @Query("lst_sessid") String sessId,
            @Query("entity_id") long entityId,
            @Query("entity_type_id") int entityTypeId
    );

    //5.6.14上传附件接口
    @Multipart
    @POST("outer/router?upload")
    Observable<RestResult<UploadBean>> upload(
            @Query("lst_sessid") String sessId,
            @Query("entity_id") long entityId,
            @Query("entity_type_id") int entityTypeId,
            @Part("file\"; filename=\"image.png\"") RequestBody file
        );

    //5.1.11	升级会员学生身份接口
    @POST("outer/router?upgrade_student")
    Observable<RestResult<Object>> upgradeStudent(
            @QueryMap Map<String, Object> map
    );

    //5.1.12	升级会员导师身份接口
    @POST("outer/router?upgrade_tutor")
    Observable<RestResult<Object>> upgradeTutor(
            @QueryMap Map<String, Object> map
    );

    //5.3.3订单详情接口
    @GET("outer/router?order_get")
    Observable<RestResult<OrderDetailBean>> getOrder(
            @Query("lst_sessid") String sessId,
            @Query("id") long id
    );

    //5.6.15内容详情接口
    @GET("outer/router?content_get")
    Observable<RestResult<ContentDetailBean>> getContent(
            @Query("id") long id
    );

    //订单支付接口
    @POST("outer/router?order_pay")
    Observable<RestResult<String>> orderPay(
            @Query("id") long id,
            @Query("channel") String channel
    );

    //会员消息列表接口
    @POST("outer/router?mes_list")
    Observable<RestResult<List<MessageBean>>> getMessageList(
            @Query("lst_sessid") String lst_sessid,
            @Query("page_no") int pageNo
    );

    //消息监测接口
    @POST("outer/router?mes_check")
    Observable<RestResult<String>> getMessageCheck(
            @Query("lst_sessid") String lst_sessid
    );

    //消息监测接口
    @GET("outer/router?fav_list")
    Observable<RestResult<List<FavBean>>> getFavList(
            @Query("lst_sessid") String lst_sessid,
            @Query("page_no") int pageNo
    );
}
