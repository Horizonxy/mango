package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.FoundListener;
import cn.com.mangopi.android.ui.widget.Clickable;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.NoUnderlineSpan;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DialogUtil;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class TrendListAdapter extends QuickAdapter<TrendBean> {

    int width;
    Context context;
    FoundListener listener;
    DisplayImageOptions options;
    List<Constants.UserIndentity> indentityList;

    public TrendListAdapter(Context context, int layoutResId, List<TrendBean> data, FoundListener listener) {
        super(context, layoutResId, data);
        this.context = context;
        this.width = (int) ((DisplayUtils.screenWidth(context)
                - context.getResources().getDimension(R.dimen.dp_15) * 2
                - context.getResources().getDimension(R.dimen.dp_5) * 2) / 3);
        this.listener = listener;
        this.options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        indentityList = MangoUtils.getIndentityList();
    }

    @Override
    protected void convert(BaseAdapterHelper helper, TrendBean item) {
        helper.setText(R.id.tv_publisher_name, item.getPublisher_name());
        helper.setText(R.id.tv_publish_time_labe, DateUtils.getShowTime(item.getPublish_time()));
        helper.setImageUrl(R.id.iv_publisher_avatar, item.getAvatar_rsurl());
        String content = item.getContent();
        TextView tvContent = helper.getView(R.id.tv_content);
        setContent(tvContent, content, item.getId(), item.getForward_trend());
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
        setPictures(helper.getView(R.id.iv_picture), helper.getView(R.id.gv_picture), pictures);

        ItemOnClickListener clickListener = new ItemOnClickListener(item);
        helper.getView(R.id.layout_comment).setOnClickListener(clickListener);
        helper.getView(R.id.layout_like).setOnClickListener(clickListener);
        helper.getView(R.id.iv_right).setOnClickListener(clickListener);
        helper.setOnClickListener(R.id.layout_share, clickListener);

        helper.setVisible(R.id.v_line, helper.getPosition() < (data.size() - 1));

        TrendBean forwardTrend = item.getForward_trend();
        if(forwardTrend == null) {
            helper.setVisible(R.id.layout_forward, false);
        } else {
            helper.setVisible(R.id.layout_forward, true);
            helper.setOnClickListener(R.id.layout_forward, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityBuilder.startTrendCommentsActivity((Activity) context, forwardTrend.getId());
                }
            });
            TextView tvForwardContent = helper.getView(R.id.tv_forward_content);
            String forwardContent = forwardTrend.getPublisher_name()+"："+forwardTrend.getContent();
            if(forwardContent != null && forwardContent.length() > 200){
                String newContent = forwardContent + "..."+"  点开全文";
                tvForwardContent.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
                SpannableString spannableString = new SpannableString(newContent);
                spannableString.setSpan(new Clickable(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityBuilder.startTrendCommentsActivity((Activity) context, forwardTrend.getId());
                    }
                }), newContent.length() - 4, newContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new NoUnderlineSpan(),  newContent.length() - 4, newContent.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                spannableString.setSpan(new TextAppearanceSpan(context, R.style.textview_sp16_333333), 0, forwardTrend.getPublisher_name().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new TextAppearanceSpan(context, R.style.textview_sp14_666666), forwardTrend.getPublisher_name().length() + 1, newContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvForwardContent.setText(spannableString, TextView.BufferType.SPANNABLE);
                tvForwardContent.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                SpannableString spannableString = new SpannableString(forwardContent);
                spannableString.setSpan(new TextAppearanceSpan(context, R.style.textview_sp16_333333), 0, forwardTrend.getPublisher_name().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new TextAppearanceSpan(context, R.style.textview_sp14_666666), forwardTrend.getPublisher_name().length() + 1, forwardContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvForwardContent.setText(spannableString, TextView.BufferType.SPANNABLE);
            }

            setPictures(helper.getView(R.id.iv_forward_picture), helper.getView(R.id.gv_forward_picture), forwardTrend.getPic_rsurls());
        }
    }

    private void setPictures(ImageView ivPicture, GridView gvPicture, List<String> pictures){
        if(pictures == null  || pictures.size() == 0){
            ivPicture.setVisibility(View.GONE);
            gvPicture.setVisibility(View.GONE);
        } else {
            if(pictures.size() == 1){
                ivPicture.setVisibility(View.VISIBLE);
                gvPicture.setVisibility(View.GONE);
                ivPicture.setImageResource(0);
                Application.application.getImageLoader().displayImage(pictures.get(0), ivPicture, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        int width = loadedImage.getWidth();
                        int height = loadedImage.getHeight();
                        float scale = DisplayUtils.dip2px(context, 180) * 1F / height;

                        Matrix matrix = new Matrix();
                        matrix.setScale(scale, scale);
                        Bitmap bitmap = Bitmap.createBitmap(loadedImage, 0, 0, width, height, matrix, false);
                        ivPicture.setImageBitmap(bitmap);
                    }
                });

                MangoUtils.showBigPicture(ivPicture, pictures.get(0));
            } else {
                ivPicture.setVisibility(View.GONE);
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
                gvPicture.setAdapter(new QuickAdapter<String>(context, R.layout.gridview_item_picture, pictures) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, String item) {
                        ImageView ivPicture = helper.getView(R.id.iv_item);
                        ivPicture.setImageResource(0);
                        AbsListView.LayoutParams params = (AbsListView.LayoutParams) ivPicture.getLayoutParams();
                        params.width = params.height = width;
                        ivPicture.setLayoutParams(params);
                        Application.application.getImageLoader().displayImage(item, ivPicture, options);
                        MangoUtils.showBigPictures(gvPicture, pictures, ivPicture, helper.getPosition());
                    }
                });
            }
        }
    }

    private void setContent(TextView tvContent, String content, long id, TrendBean forwardTrend){
        if(content != null && content.length() > 200){
            String newContent = content + "..."+"  点开全文";
            tvContent.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
            SpannableString spannableString = new SpannableString(newContent);
            spannableString.setSpan(new Clickable(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityBuilder.startTrendCommentsActivity((Activity) context, id);
                }
            }), newContent.length() - 4, newContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new NoUnderlineSpan(),  newContent.length() - 4, newContent.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvContent.setText(spannableString);
            tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            tvContent.setText(content);
        }
    }

    private void setForwardContent(TextView tvForwardContent, String forwardContent){
        if(forwardContent != null && forwardContent.length() > 200){
            String newContent = forwardContent + "..."+"  点开全文";
            tvForwardContent.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
            SpannableString spannableString = new SpannableString(newContent);
            spannableString.setSpan(new Clickable(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }), newContent.length() - 4, newContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new NoUnderlineSpan(),  newContent.length() - 4, newContent.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tvForwardContent.setText(spannableString, TextView.BufferType.SPANNABLE);
            tvForwardContent.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            tvForwardContent.setText(forwardContent, TextView.BufferType.SPANNABLE);
        }
    }

    class ItemOnClickListener implements View.OnClickListener {

        TrendBean trend;

        public ItemOnClickListener(TrendBean trend) {
            this.trend = trend;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_share:
                    if(indentityList.contains(Constants.UserIndentity.COMMUNITY) || indentityList.contains(Constants.UserIndentity.TUTOR)) {
                        ActivityBuilder.startTrendForwardActivity((Activity) context, trend);
                    } else {
                        DialogUtil.createChoosseDialog(context, "提示", "只有升级成为导师或社团才能转发哦", "去认证", "暂不认证", new DialogUtil.OnChooseDialogListener() {
                            @Override
                            public void onChoose() {
                                ActivityBuilder.startUpgradeRoleActivity((Activity) context);
                            }
                        });
                    }
                    break;
                case R.id.layout_comment:
                    listener.comment(trend);
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
