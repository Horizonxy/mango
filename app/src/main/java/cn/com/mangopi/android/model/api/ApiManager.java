package cn.com.mangopi.android.model.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.model.bean.ActorTeamBean;
import cn.com.mangopi.android.model.bean.AdvertBean;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.model.bean.CalcPriceBean;
import cn.com.mangopi.android.model.bean.CommunityClassifyBean;
import cn.com.mangopi.android.model.bean.CommunityTypeBean;
import cn.com.mangopi.android.model.bean.CompanyDetailBean;
import cn.com.mangopi.android.model.bean.ContentDetailBean;
import cn.com.mangopi.android.model.bean.CouponBean;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.model.bean.CourseCouponBean;
import cn.com.mangopi.android.model.bean.CourseDetailBean;
import cn.com.mangopi.android.model.bean.CourseTypeBean;
import cn.com.mangopi.android.model.bean.FavBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.bean.MessageBean;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.OrderDetailBean;
import cn.com.mangopi.android.model.bean.ProjectActorBean;
import cn.com.mangopi.android.model.bean.ProjectDetailBean;
import cn.com.mangopi.android.model.bean.ProjectJoinBean;
import cn.com.mangopi.android.model.bean.ProjectListBean;
import cn.com.mangopi.android.model.bean.ProjectTeamBean;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.ScheduleCalendarBean;
import cn.com.mangopi.android.model.bean.SearchBean;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.model.bean.TrendDetailBean;
import cn.com.mangopi.android.model.bean.TutorBean;
import cn.com.mangopi.android.model.bean.UploadBean;
import cn.com.mangopi.android.model.bean.ReplyTrendBean;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.QueryMap;
import rx.Observable;

public class ApiManager {


    public static Observable<RestResult<RegistBean>> getLoginVerifyCode(String mobile){
        return Application.application.getApiService().getLoginVerifyCode(mobile);
    }

    public static  Observable<RestResult<RegistBean>> quickLogin(Map<String, Object> map, String sessId){
        return Application.application.getApiService().quickLogin(sessId, map);
    }

    public static  Observable<RestResult<RegistBean>> wxLogin(String openId){
        return Application.application.getApiService().wxLogin(openId);
    }

    public static  Observable<RestResult<RegistBean>> wxLogin(String openId, String unionId){
        return Application.application.getApiService().wxLogin(openId, unionId);
    }

    public static  Observable<RestResult<Object>> updateMember(String nickName, int gender, String sessId){
        return Application.application.getApiService().updateMember(nickName, gender, sessId);
    }

    public static  Observable<RestResult<MemberBean>> getMember(long id){
        return Application.application.getApiService().getMember(id);
    }

    public static  Observable<RestResult<ArrayList<AdvertBean>>> getAdvert(String userIdentity, String position){
        return Application.application.getApiService().getAdvert(userIdentity, position);
    }

    public static  Observable<RestResult<ArrayList<BulletinBean>>> getBulletinList(int pageNo, int pageSize){
        return Application.application.getApiService().getBulletinList(pageNo, pageSize);
    }

    public static  Observable<RestResult<ArrayList<CourseClassifyBean>>> getClassifyList(Map<String, Long> map){
        return Application.application.getApiService().getClassifyList(map);
    }

    public static  Observable<RestResult<ArrayList<CourseTypeBean>>> getTypeList(){
        return Application.application.getApiService().getTypeList();
    }

    public static  Observable<RestResult<List<TrendBean>>> getTrendList(Map<String, Object> map){
        return Application.application.getApiService().getTrendList(map);
    }

    public static  Observable<RestResult<Object>> praise(long entityId, int entityTypeId){
        return Application.application.getApiService().praise(Application.application.getSessId(), entityId, entityTypeId);
    }

    public static  Observable<RestResult<Object>> praiseTrend(long id){
        return Application.application.getApiService().praiseTrend(Application.application.getSessId(), id);
    }

    public static  Observable<RestResult<Object>> addTrend(String content, List<String> pics){
        return Application.application.getApiService().addTrend(Application.application.getSessId(), content, pics);
    }

    public static  Observable<RestResult<Object>> forwardTrend(String content, long fawordTrendId){
        return Application.application.getApiService().forwardTrend(Application.application.getSessId(), content, fawordTrendId);
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

    public static  Observable<RestResult<Object>> checkUpgradeTutor(){
        return Application.application.getApiService().checkUpgradeTutor(Application.application.getSessId());
    }

    public static  Observable<RestResult<Object>> checkUpgradeStudent(){
        return Application.application.getApiService().checkUpgradeStudent(Application.application.getSessId());
    }

    public static  Observable<RestResult<Object>> checkUpgradeCompany(){
        return Application.application.getApiService().checkUpgradeCompany(Application.application.getSessId());
    }

    public static  Observable<RestResult<Object>> checkUpgradeCommunity(){
        return Application.application.getApiService().checkUpgradeCommunity(Application.application.getSessId());
    }

    public static  Observable<RestResult<TutorBean>> getTutor(long id){
        return Application.application.getApiService().getTutor(id, Application.application.getSessId());
    }

    public static  Observable<RestResult<CourseDetailBean>> getCourse(long id){
        return Application.application.getApiService().getCourse(id, Application.application.getSessId());
    }

    public static  Observable<RestResult<OrderBean>> addOrder(Map<String, Object> map){
        return Application.application.getApiService().addOrder(map);
    }

    public static  Observable<RestResult<Object>> addFav(long entityId, int entityTypeId){
        return Application.application.getApiService().addFav(Application.application.getSessId(), entityId, entityTypeId);
    }

    public static  Observable<RestResult<Object>> delFav(long entityId, int entityTypeId){
        return Application.application.getApiService().delFav(Application.application.getSessId(), entityId, entityTypeId);
    }

    public static  Observable<RestResult<Object>> wantCount(long entityId, int entityTypeId){
        return Application.application.getApiService().wantCount(Application.application.getSessId(), entityId, entityTypeId);
    }

    public static  Observable<RestResult<UploadBean>> upload(long entityId, int entityTypeId, MultipartBody.Part file){
        return Application.application.getApiService().upload(Application.application.getSessId(), entityId, entityTypeId, file);
    }

    public static  Observable<RestResult<Object>> upgradeStudent(Map<String, Object> map){
        return Application.application.getApiService().upgradeStudent(map);
    }

    public static  Observable<RestResult<Object>> upgradeTutor(Map<String, Object> map){
        return Application.application.getApiService().upgradeTutor(map);
    }

    public static  Observable<RestResult<Object>> upgradeCommunity(Map<String, Object> map){
        return Application.application.getApiService().upgradeCommunity(map);
    }

    public static  Observable<RestResult<Object>> upgradeCompany(Map<String, Object> map){
        return Application.application.getApiService().upgradeCompany(map);
    }

    public static  Observable<RestResult<CompanyDetailBean>> getCompany(String companyNo){
        return Application.application.getApiService().getCompany(companyNo);
    }

    public static  Observable<RestResult<List<CommunityTypeBean>>> getCommunityTypeList(){
        return Application.application.getApiService().getCommunityTypeList();
    }

    public static  Observable<RestResult<List<CommunityClassifyBean>>> getCommunityClassifyList(){
        return Application.application.getApiService().getCommunityClassifyList();
    }

    public static  Observable<RestResult<OrderDetailBean>> getOrder(long id){
        return Application.application.getApiService().getOrder(Application.application.getSessId(), id);
    }

    public static  Observable<RestResult<ContentDetailBean>> getContent(long id){
        return Application.application.getApiService().getContent(id);
    }

    public static  Observable<RestResult<String>> orderPay(long id, String channel){
        return Application.application.getApiService().orderPay(id, channel);
    }

    public static  Observable<RestResult<List<MessageBean>>> getMessageList(int pageNo){
        return Application.application.getApiService().getMessageList(Application.application.getSessId(), pageNo);
    }

    public static  Observable<RestResult<String>> getMessageCheck(){
        return Application.application.getApiService().getMessageCheck(Application.application.getSessId());
    }

    public static  Observable<RestResult<List<FavBean>>> getFavList(int pageNo){
        return Application.application.getApiService().getFavList(Application.application.getSessId(), pageNo);
    }

    public static  Observable<RestResult<BulletinBean>> getBulletin(long id){
        return Application.application.getApiService().getBulletin(id);
    }

    public static  Observable<RestResult<Object>> addCourse(Map<String, Object> map){
        return Application.application.getApiService().addCourse(map);
    }

    public static  Observable<RestResult<Object>> delCourse(long id){
        return Application.application.getApiService().delCourse(Application.application.getSessId(), id);
    }

    public static  Observable<RestResult<Object>> readMessage(long id){
        return Application.application.getApiService().readMessage(Application.application.getSessId(), id);
    }

    public static  Observable<RestResult<Object>> cancelOrder(long id){
        return Application.application.getApiService().cancelOrder(Application.application.getSessId(), id);
    }

    public static  Observable<RestResult<Object>> settingMember(Map<String, Object> map){
        return Application.application.getApiService().settingMember(map);
    }

    public static  Observable<RestResult<Object>> walletDraw(long cardId, BigDecimal amount){
        return Application.application.getApiService().walletDraw(Application.application.getSessId(), cardId, amount);
    }

    public static  Observable<RestResult<List<SearchBean>>> fullSearch(String searchText, int pageNo){
        return Application.application.getApiService().fullSearch(searchText, pageNo, Constants.PAGE_SIZE);
    }

    public static  Observable<RestResult<List<ProjectListBean>>> getProjectList(int pageNo, int relation){
        return Application.application.getApiService().getProjectList(Application.application.getSessId(), pageNo, Constants.PAGE_SIZE, relation);
    }

    public static  Observable<RestResult<ProjectDetailBean>> getProject(long id){
        return Application.application.getApiService().getProject(Application.application.getSessId(), id);
    }

    public static  Observable<RestResult<TrendDetailBean>> getTrend(long id){
        return Application.application.getApiService().getTrend(id);
    }

    public static  Observable<RestResult<List<TransListBean>>> walletTransList(int pageNo){
        return Application.application.getApiService().walletTransList(Application.application.getSessId(), pageNo, Constants.PAGE_SIZE);
    }

    public static  Observable<RestResult<List<ScheduleCalendarBean>>> scheduleCalendar(@QueryMap Map<String, Object> map){
        return Application.application.getApiService().scheduleCalendar(Application.application.getSessId(), map);
    }

    public static  Observable<RestResult<ProjectJoinBean>> projectJoin(Map<String, Object> map){
        return Application.application.getApiService().projectJoin(Application.application.getSessId(), map);
    }

    public static Observable<RestResult<List<ProjectTeamBean>>> projectTeamList(long id){
        return Application.application.getApiService().projectTeamList(id);
    }

    public static Observable<RestResult<Object>> delCard(long id){
        return Application.application.getApiService().delCard(Application.application.getSessId(), id);
    }

    public static Observable<RestResult<ActorTeamBean>> getActorTeam(long id){
        return Application.application.getApiService().getActorTeam(id);
    }

    public static Observable<RestResult<ProjectActorBean>> getProjectActor(long id){
        return Application.application.getApiService().getProjectActor(id);
    }

    public static Observable<RestResult<Object>> offCourse(long id){
        return Application.application.getApiService().offCourse(Application.application.getSessId(), id);
    }

    public static Observable<RestResult<ReplyTrendBean>> replyTrend(Map<String, Object> map){
        return Application.application.getApiService().replyTrend(Application.application.getSessId(), map);
    }

    public static Observable<RestResult<Object>> trendUpdate(String current){
        return Application.application.getApiService().trendUpdate(Application.application.getSessId(), current, 3);
    }

    public static Observable<RestResult<Object>> addOrderSchedule(long orderId, String date, int time){
        return Application.application.getApiService().addOrderSchedule(Application.application.getSessId(), orderId, date, time);
    }

    public static Observable<RestResult<Object>> cancelOrderBatchSchedule(String date, int time){
        return Application.application.getApiService().cancelOrderBatchSchedule(Application.application.getSessId(), date, time);
    }

    public static Observable<RestResult<Object>> cancelOrderSchedule(long orderId){
        return Application.application.getApiService().cancelOrderSchedule(Application.application.getSessId(), orderId);
    }

    public static Observable<RestResult<Object>> addCourseComment(long orderId, long courseId, String content){
        return Application.application.getApiService().addCourseComment(Application.application.getSessId(), orderId, courseId, content);
    }

    public static Observable<RestResult<Object>> replyCourseComment(long commentId, String reply){
        return Application.application.getApiService().replyCourseComment(Application.application.getSessId(), commentId, reply);
    }

    public static Observable<RestResult<Object>> actorComment(long id, int score, String comment){
        return Application.application.getApiService().actorComment(Application.application.getSessId(), id, score, comment);
    }

    public static Observable<RestResult<List<CouponBean>>> memberCouponList(int pageNo, int state){
        return Application.application.getApiService().memberCouponList(Application.application.getSessId(), pageNo, Constants.PAGE_SIZE, state);
    }

    public static Observable<RestResult<List<CourseCouponBean>>> promotion(long courseId){
        return Application.application.getApiService().promotion(Application.application.getSessId(), courseId);
    }

    public static Observable<RestResult<Object>> fetchCoupon(long couponId){
        return Application.application.getApiService().fetchCoupon(Application.application.getSessId(), couponId);
    }

    public static Observable<RestResult<CalcPriceBean>> calcPrice(Map<String, Object> map){
        return Application.application.getApiService().calcPrice(Application.application.getSessId(), map);
    }
}
