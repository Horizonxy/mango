package cn.com.mangopi.android.di.module;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.ActivityScope;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.model.bean.TrendDetailBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.popupwindow.InputPopupWindow;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.MangoUtils;
import dagger.Module;
import dagger.Provides;

@Module
public class TrendCommentsActivityModule {

    Activity activity;
    List datas;
    List<Constants.UserIndentity> indentityList;
    MemberBean member;
    TrendDetailBean trendDetail;
    OnReplyListener replyListener;

    public TrendCommentsActivityModule(Activity activity, List datas, OnReplyListener replyListener) {
        this.activity = activity;
        this.datas = datas;
        indentityList = MangoUtils.getIndentityList();
        member = Application.application.getMember();
        this.replyListener = replyListener;
    }

    @ActivityScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<TrendDetailBean.Comment>(activity, R.layout.listview_item_interact_area, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, TrendDetailBean.Comment item) {
                helper.setText(R.id.tv_name, item.getMember_name())
                        .setText(R.id.tv_time, DateUtils.getShowTime(item.getComment_time()))
                        .setText(R.id.tv_content, item.getContent())
                        .setVisible(R.id.iv_comment, (indentityList.contains(Constants.UserIndentity.TUTOR) || indentityList.contains(Constants.UserIndentity.COMMUNITY))
                                && TextUtils.isEmpty(item.getReply()) && trendDetail != null && member != null && trendDetail.getPublisher_id() == member.getId())
                        .setVisible(R.id.tv_reply, !TextUtils.isEmpty(item.getReply()))
                        .setText(R.id.tv_reply, item.getReply())
                        .setImageUrl(R.id.iv_avatar, item.getAvatar_rsurl())
                .setOnClickListener(R.id.iv_comment, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(replyListener != null){
                            replyListener.onReply(item);
                        }
                    }
                });
            }
        };
    }

    public void setTrendDetail(TrendDetailBean trendDetail) {
        this.trendDetail = trendDetail;
    }

    public interface OnReplyListener{
        void onReply(TrendDetailBean.Comment comment);
    }
}
