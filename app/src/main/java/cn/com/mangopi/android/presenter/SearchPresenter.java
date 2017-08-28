package cn.com.mangopi.android.presenter;

import java.util.List;

import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.RestResult;
import cn.com.mangopi.android.model.bean.SearchBean;
import cn.com.mangopi.android.model.data.SearchModel;
import cn.com.mangopi.android.ui.viewlistener.SearchListener;
import rx.Subscription;
import rx.functions.Action1;

public class SearchPresenter extends BasePresenter {

    SearchModel searchModel;
    SearchListener searchListener;

    public SearchPresenter(SearchModel searchModel, SearchListener searchListener) {
        this.searchModel = searchModel;
        this.searchListener = searchListener;
    }

    public void fullSearch(){
        Subscription subscription = searchModel.fullSearch(searchListener.getSearchText(), searchListener.getPageNo(), new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                if (e != null) {
                    e.printStackTrace();
                }
                searchListener.onFailure(null);
            }
        }, new BaseSubscriber<RestResult<List<SearchBean>>>(){
            @Override
            public void onNext(RestResult<List<SearchBean>> restResult) {
                super.onNext(restResult);
                if (restResult == null) {
                    searchListener.onFailure(null);
                } else {
                    if (restResult.isSuccess()) {
                        searchListener.onSearchList(restResult.getData());
                    } else {
                        searchListener.onFailure(restResult.getRet_msg());
                    }
                }
            }
        });
        addSubscription(subscription);
    }
}
