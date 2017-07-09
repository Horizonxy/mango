package com.mango.di.module;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.mango.Application;
import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.model.bean.TrendBean;
import com.mango.model.data.PraiseModel;
import com.mango.model.data.TrendModel;
import com.mango.presenter.FoundPresenter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.FoundListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.DisplayUtils;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@Module
public class FoundFragmentModule {

    Fragment fragment;
    List datas;

    public FoundFragmentModule( Fragment fragment, List datas) {
        this.fragment = fragment;
        this.datas = datas;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<TrendBean>(fragment.getContext(), R.layout.listview_item_found, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, TrendBean item) {
                helper.setText(R.id.tv_publisher_name, item.getPublisher_name());
                helper.setText(R.id.tv_publish_time_labe, item.getPublish_time_label());
                helper.setImageUrl(R.id.iv_publisher_avatar, item.getAvatar_rsurl());
                helper.setText(R.id.tv_content, item.getContent());
                if(TextUtils.isEmpty(item.getCity())){
                    helper.setVisible(R.id.tv_city, false);
                } else {
                    helper.setVisible(R.id.tv_city, true);
                    helper.setText(R.id.tv_city, item.getCity());
                }
                helper.setText(R.id.tv_faword_count, String.valueOf(item.getFaword_count()));
                helper.setText(R.id.tv_comment_count, String.valueOf(item.getComment_count()));
                helper.setText(R.id.tv_praise_count, String.valueOf(item.getPraise_count()));

                List<String> pictures = item.getPic_rsurls();
                if(pictures == null  || pictures.size() == 0){
                    helper.setVisible(R.id.iv_picture, false);
                    helper.setVisible(R.id.gv_picture, false);
                } else {
                    if(pictures.size() == 1){
                        helper.setVisible(R.id.iv_picture, true);
                        helper.setVisible(R.id.gv_picture, false);
                        helper.setImageUrl(R.id.iv_picture, pictures.get(0));
                    } else {
                        helper.setVisible(R.id.iv_picture, false);
                        helper.setVisible(R.id.gv_picture, true);
                        helper.setAdapter(R.id.gv_picture, new QuickAdapter<String>(fragment.getContext(), R.layout.gridview_item_picture, pictures) {
                            @Override
                            protected void convert(BaseAdapterHelper helper, String item) {
                                ImageView ivPicture = helper.getView(R.id.iv_item);
                                AbsListView.LayoutParams params = (AbsListView.LayoutParams) ivPicture.getLayoutParams();
                                params.width = params.height = (int) ((DisplayUtils.screenWidth(fragment.getContext()) - fragment.getContext().getResources().getDimension(R.dimen.dp_15) * 4) / 3);
                                ivPicture.setLayoutParams(params);
                                Application.application.getImageLoader().displayImage(item, ivPicture, Application.application.getDefaultOptions());
                            }
                        });
                    }
                }
                helper.getView(R.id.layout_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityBuilder.startInteractAreaActivity(fragment.getActivity());
                    }
                });
                helper.getView(R.id.layout_like).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FoundListener)fragment).praise(item);
                    }
                });
            }
        };
    }

    @FragmentScope
    @Provides
    public FoundPresenter provideFoundPresenter(){
        return new FoundPresenter(new PraiseModel(), new TrendModel(), (FoundListener) fragment);
    }
}
