package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;

public class TrendListAdapter extends QuickAdapter<TrendBean> {

    int width;
    Context context;
    FoundListener listener;

    public TrendListAdapter(Context context, int layoutResId, List<TrendBean> data, FoundListener listener) {
        super(context, layoutResId, data);
        this.context = context;
        this.width = (int) ((DisplayUtils.screenWidth(context)
                - context.getResources().getDimension(R.dimen.dp_15) * 2
                - context.getResources().getDimension(R.dimen.dp_5) * 2) / 3);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, TrendBean item) {
        helper.setText(R.id.tv_publisher_name, item.getPublisher_name());
        helper.setText(R.id.tv_publish_time_labe, DateUtils.getShowTime(item.getPublish_time()));
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
        helper.setImageResource(R.id.iv_right, item.is_favor() ? R.drawable.icon_shoucang : R.drawable.icon_shoucang_nor);

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
                GridView gvPicture =  helper.getView(R.id.gv_picture);
                gvPicture.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams gvParams = (LinearLayout.LayoutParams) gvPicture.getLayoutParams();
                if(pictures.size() == 4) {
                    gvPicture.setNumColumns(2);
                    gvParams.width = width * 2 + DisplayUtils.dip2px(context, 2);
                } else {
                    gvPicture.setNumColumns(3);
                    gvParams.width = width * 3 + DisplayUtils.dip2px(context, 2) * 2;
                }
                gvPicture.setLayoutParams(gvParams);
                helper.setAdapter(R.id.gv_picture, new QuickAdapter<String>(context, R.layout.gridview_item_picture, pictures) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, String item) {
                        ImageView ivPicture = helper.getView(R.id.iv_item);
                        AbsListView.LayoutParams params = (AbsListView.LayoutParams) ivPicture.getLayoutParams();
                        params.width = params.height = width;
                        ivPicture.setLayoutParams(params);
                        Application.application.getImageLoader().displayImage(item, ivPicture, Application.application.getDefaultOptions());
                    }
                });
            }
        }

        ItemOnClickListener clickListener = new ItemOnClickListener(item);
        helper.getView(R.id.layout_comment).setOnClickListener(clickListener);
        helper.getView(R.id.layout_like).setOnClickListener(clickListener);
        helper.getView(R.id.iv_right).setOnClickListener(clickListener);
    }

    class ItemOnClickListener implements View.OnClickListener {

        TrendBean trend;

        public ItemOnClickListener(TrendBean trend) {
            this.trend = trend;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_comment:
                    ActivityBuilder.startInteractAreaActivity((Activity) context);
                    break;
                case R.id.layout_like:
                    listener.praise(trend);
                    break;
                case R.id.iv_right:
                    listener.delOrAddFav(trend);
                    break;
            }
        }
    }
}