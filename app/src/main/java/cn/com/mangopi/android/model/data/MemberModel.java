package cn.com.mangopi.android.model.data;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.CompanyDetailBean;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.bean.RegistBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.TransListBean;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MemberModel {

    public Subscription getLoginVerifyCode(String mobile, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.getLoginVerifyCode(mobile)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription quickLogin(String mobile, String smsCode, String sessId, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.quickLogin(mobile, smsCode, sessId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public Subscription wxLogin(String openId, Action0 onSubscribe, Subscriber<RestResult<RegistBean>> subscriber){
        return ApiManager.wxLogin(openId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription updateMember(String nickName, int gender, String sessId, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return ApiManager.updateMember(nickName, gender, sessId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(onSubscribe)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getMember(long id, Action0 onSubscribe, Subscriber<RestResult<MemberBean>> subscriber){
       return ApiManager.getMember(id)
               .subscribeOn(Schedulers.io())
               .doOnSubscribe(onSubscribe)
               .subscribeOn(AndroidSchedulers.mainThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(subscriber);
    }

    public Subscription getWallet(Action0 onSubscribe, Subscriber<RestResult<MemberWalletBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getWallet(), onSubscribe).subscribe(subscriber);
    }

    public Subscription getCardList(Action0 onSubscribe, Subscriber<RestResult<List<MemberCardBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getCardList(), onSubscribe).subscribe(subscriber);
    }

    public Subscription addBlankCard(String blankName, String cardNo, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.addBlankCard(blankName, cardNo), onSubscribe).subscribe(subscriber);
    }

    public Subscription checkUpgradeStudent(Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.checkUpgradeStudent(), onSubscribe).subscribe(subscriber);
    }

    public Subscription checkUpgradeTutor(Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.checkUpgradeTutor(), onSubscribe).subscribe(subscriber);
    }

    public Subscription checkUpgradeCompany(Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.checkUpgradeCompany(), onSubscribe).subscribe(subscriber);
    }

    public Subscription checkUpgradeCommunity(Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.checkUpgradeCommunity(), onSubscribe).subscribe(subscriber);
    }

    public Subscription upgradeStudent(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.upgradeStudent(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription upgradeTutor(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.upgradeTutor(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription upgradeCommunity(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.upgradeCommunity(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription upgradeCompany(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.upgradeCompany(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription getCompany(String companyNo, Action0 onSubscribe, Subscriber<RestResult<CompanyDetailBean>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.getCompany(companyNo), onSubscribe).subscribe(subscriber);
    }

    public Subscription settingMember(Map<String, Object> map, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.settingMember(map), onSubscribe).subscribe(subscriber);
    }

    public Subscription walletDraw(long cardId, BigDecimal amount, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.walletDraw(cardId, amount), onSubscribe).subscribe(subscriber);
    }

    public Subscription walletTransList(int pageNo, Action1 onError,  Subscriber<RestResult<List<TransListBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.walletTransList(pageNo), onError).subscribe(subscriber);
    }

    public Subscription delCard(long id, Action0 onSubscribe, Subscriber<RestResult<Object>> subscriber){
        return RxJavaUtils.schedulersIoMainOnSubscribe(ApiManager.delCard(id), onSubscribe).subscribe(subscriber);
    }
}
