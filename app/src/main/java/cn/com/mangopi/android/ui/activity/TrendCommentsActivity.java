package cn.com.mangopi.android.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
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

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerTrendCommentsActivityComponent;
import cn.com.mangopi.android.di.module.TrendCommentsActivityModule;
import cn.com.mangopi.android.model.bean.ReplyTrendBean;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.model.bean.TrendDetailBean;
import cn.com.mangopi.android.presenter.TrendCommentsPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.TrendCommentsListener;
import cn.com.mangopi.android.ui.widget.Clickable;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.NoUnderlineSpan;
import cn.com.mangopi.android.ui.widget.RoundImageView;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MangoUtils;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class TrendCommentsActivity extends BaseTitleBarActivity implements TrendCommentsListener, TrendCommentsActivityModule.OnReplyListener {

    @Bind(R.id.listview)
    PullToRefreshListView listView;
    List<TrendDetailBean.Comment> datas = new ArrayList<TrendDetailBean.Comment>();
    @Inject
    QuickAdapter adapter;
    long id;
    TrendDetailBean trendDetail;
    TrendCommentsPresenter trendCommentsPresenter;
    TextView tvPublisherName;
    TextView tvPublisherTime;
    RoundImageView ivPublisherAvatar;
    TextView tvContent;
    ImageView ivPicture;
    GridView gvPicture;
    TextView tvCity;
    TextView tvFawordCount;
    TextView tvCommentCount;
    TextView tvPraiseCount;
    DisplayImageOptions options;
    int width;
    TrendCommentsActivityModule commentsModule;
    String replyCommentContent;
    TrendDetailBean.Comment replyComment;

    LinearLayout layoutForward;
    TextView tvForwardContent;
    ImageView ivForwardPicture;
    GridView gvForwardPicture;

    int picWidth;
    int picHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);
        DaggerTrendCommentsActivityComponent.builder().trendCommentsActivityModule(commentsModule = new TrendCommentsActivityModule(this, datas, this)).build().inject(this);
        Bus.getDefault().register(this);
        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        initView();
        trendCommentsPresenter = new TrendCommentsPresenter(this);
        trendCommentsPresenter.getTrend();

        this.options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        this.width = (int) ((DisplayUtils.screenWidth(this)
                - getResources().getDimension(R.dimen.dp_15) * 2
                - getResources().getDimension(R.dimen.dp_5) * 2) / 3);

        picWidth = DisplayUtils.screenWidth(this) - DisplayUtils.dip2px(this, 15 * 2);
        picHeight = (int) (picWidth * 320 * 1F/ 690);
    }

    private void initView() {
        View header = getLayoutInflater().inflate(R.layout.listview_item_found, null);
        listView.getRefreshableView().addHeaderView(header);
        tvPublisherName = (TextView) header.findViewById(R.id.tv_publisher_name);
        tvPublisherTime = (TextView) header.findViewById(R.id.tv_publish_time_labe);
        ivPublisherAvatar = (RoundImageView) header.findViewById(R.id.iv_publisher_avatar);
        tvContent = (TextView) header.findViewById(R.id.tv_content);
        tvCity = (TextView) header.findViewById(R.id.tv_city);
        ivPicture = (ImageView) header.findViewById(R.id.iv_picture);
        gvPicture = (GridView) header.findViewById(R.id.gv_picture);
        tvFawordCount = (TextView) header.findViewById(R.id.tv_faword_count);
        tvCommentCount = (TextView) header.findViewById(R.id.tv_comment_count);
        tvPraiseCount = (TextView) header.findViewById(R.id.tv_praise_count);
        //header.findViewById(R.id.layout_comment).setOnClickListener(this);
        layoutForward = (LinearLayout) header.findViewById(R.id.layout_forward);
        tvForwardContent = (TextView) header.findViewById(R.id.tv_forward_content);
        ivForwardPicture = (ImageView) header.findViewById(R.id.iv_forward_picture);
        gvForwardPicture = (GridView) header.findViewById(R.id.gv_forward_picture);

        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onTrendSuccess(TrendDetailBean trendDetail) {
        titleBar.setTitle(trendDetail.getPublisher_name());
        commentsModule.setTrendDetail(trendDetail);
        this.trendDetail = trendDetail;
        bindData(trendDetail);
        datas.clear();
        if(trendDetail.getComments() != null) {
            datas.addAll(trendDetail.getComments());
        }
        adapter.notifyDataSetChanged();
    }

    private void bindData(TrendDetailBean trendDetail) {
        tvPublisherName.setText(trendDetail.getPublisher_name());
        tvPublisherTime.setText(DateUtils.getShowTime(trendDetail.getPublish_time()));
        Application.application.getImageLoader().displayImage(trendDetail.getAvatar_rsurl(), ivPublisherAvatar);
        tvContent.setText(trendDetail.getContent());
        if(TextUtils.isEmpty(trendDetail.getCity())){
            tvCity.setVisibility(View.GONE);
        } else {
            tvCity.setVisibility(View.VISIBLE);
            tvCity.setText(trendDetail.getCity());
        }
        tvFawordCount.setText(String.valueOf(trendDetail.getFaword_count()));
        tvCommentCount.setText(String.valueOf(trendDetail.getComment_count()));
        tvPraiseCount.setText(String.valueOf(trendDetail.getPraise_count()));
        List<String> pictures = trendDetail.getPic_rsurls();
        setPictures(ivPicture, gvPicture, pictures);

        bindForward(trendDetail);
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
                Application.application.getImageLoader().displayImage(MangoUtils.getCalculateSizeUrl(pictures.get(0), picWidth, picHeight), ivPicture, options/*, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        int width = loadedImage.getWidth();
                        int height = loadedImage.getHeight();
                        float scale = picHeight * 1F / height;

                        Matrix matrix = new Matrix();
                        matrix.setScale(scale, scale);
                        Bitmap bitmap = Bitmap.createBitmap(loadedImage, 0, 0, width, height, matrix, false);
                        ivPicture.setImageBitmap(bitmap);
                    }
                }*/);
                MangoUtils.showBigPicture(ivPicture, pictures.get(0));
            } else {
                ivPicture.setVisibility(View.GONE);
                gvPicture.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams gvParams = (LinearLayout.LayoutParams) gvPicture.getLayoutParams();
                if(pictures.size() == 4) {
                    gvPicture.setNumColumns(2);
                    gvParams.width = width * 2 + DisplayUtils.dip2px(this, 2);
                } else {
                    gvPicture.setNumColumns(3);
                    gvParams.width = width * 3 + DisplayUtils.dip2px(this, 2) * 2;
                }
                gvPicture.setLayoutParams(gvParams);
                gvPicture.setAdapter(new QuickAdapter<String>(this, R.layout.gridview_item_picture, pictures) {
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

    private void bindForward(TrendDetailBean trendDetail){
        TrendBean forwardTrend = trendDetail.getForward_trend();

        if(forwardTrend == null) {
            layoutForward.setVisibility(View.GONE);
        } else {
            layoutForward.setVisibility(View.VISIBLE);
            layoutForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityBuilder.startTrendCommentsActivity(TrendCommentsActivity.this, forwardTrend.getId());
                }
            });
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
                spannableString.setSpan(new TextAppearanceSpan(this, R.style.textview_sp16_333333), 0, forwardTrend.getPublisher_name().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new TextAppearanceSpan(this, R.style.textview_sp14_666666), forwardTrend.getPublisher_name().length() + 1, newContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvForwardContent.setText(spannableString, TextView.BufferType.SPANNABLE);
                tvForwardContent.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                SpannableString spannableString = new SpannableString(forwardContent);
                spannableString.setSpan(new TextAppearanceSpan(this, R.style.textview_sp16_333333), 0, forwardTrend.getPublisher_name().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new TextAppearanceSpan(this, R.style.textview_sp14_666666), forwardTrend.getPublisher_name().length() + 1, forwardContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvForwardContent.setText(spannableString, TextView.BufferType.SPANNABLE);
            }

            setPictures(ivPicture, gvForwardPicture, forwardTrend.getPic_rsurls());
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Map<String, Object> replyCommentMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trend_id", id);
        map.put("comment_id", replyComment.getId());
        map.put("content", replyCommentContent);
        return map;
    }

    @Override
    public void onReplyCommentSuccess(ReplyTrendBean replyTrendBean) {
        replyComment.setReply(replyTrendBean.getReply());
        adapter.notifyDataSetChanged();
        replyCommentContent = "";
        replyComment = null;
    }

    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event) {
        String type = event.getType();
        if("reply_comment".equals(type)){
            replyCommentContent = event.getContent();
            trendCommentsPresenter.replyComment();
        }
    }

    @Override
    public void onReply(TrendDetailBean.Comment comment) {
        replyComment = comment;
        ActivityBuilder.startInputMessageActivity(this, "回复评论", "确定", "reply_comment", "回复 "+comment.getMember_name(), 100);
    }

    @Override
    protected void onDestroy() {
        if(trendCommentsPresenter != null){
            trendCommentsPresenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }
}
