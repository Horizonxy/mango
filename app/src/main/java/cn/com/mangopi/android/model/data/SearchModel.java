package cn.com.mangopi.android.model.data;

import java.util.List;
import cn.com.mangopi.android.model.api.ApiManager;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.SearchBean;
import cn.com.mangopi.android.util.RxJavaUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public class SearchModel {

    public Subscription fullSearch(String searchText, int pageNo, Action1 onError, Subscriber<RestResult<List<SearchBean>>> subscriber){
        return RxJavaUtils.schedulersIoMainError(ApiManager.fullSearch(searchText, pageNo), onError).subscribe(subscriber);
    }
}
