package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import cn.com.mangopi.android.ui.popupwindow.InputPopupWindow;
import cn.com.mangopi.android.ui.viewlistener.TrendCommentsListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.RoundImageView;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.MangoUtils;

public class TrendCommentsActivity extends BaseTitleBarActivity implements TrendCommentsListener, View.OnClickListener {

    @Bind(R.id.listview)
    PullToRefreshListView listView;
    List<TrendDetailBean.Comment> datas = new ArrayList<TrendDetailBean.Comment>();
    @Inject
    QuickAdapter adapter;
    long id;
    TrendBean trendBean;
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
    String replyTrendContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_listview);
        DaggerTrendCommentsActivityComponent.builder().trendCommentsActivityModule(new TrendCommentsActivityModule(this, datas)).build().inject(this);

        id = getIntent().getLongExtra(Constants.BUNDLE_ID, 0);
        trendBean = (TrendBean) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);
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
        header.findViewById(R.id.layout_comment).setOnClickListener(this);
        tvPublisherName.setText(trendBean.getPublisher_name());
        tvPublisherTime.setText(DateUtils.getShowTime(trendBean.getPublish_time()));
        Application.application.getImageLoader().displayImage(trendBean.getAvatar_rsurl(), ivPublisherAvatar);
        tvContent.setText(trendBean.getContent());
        if(TextUtils.isEmpty(trendBean.getCity())){
            tvCity.setVisibility(View.GONE);
        } else {
            tvCity.setVisibility(View.VISIBLE);
            tvCity.setText(trendBean.getCity());
        }
        tvFawordCount.setText(String.valueOf(trendBean.getFaword_count()));
        tvCommentCount.setText(String.valueOf(trendBean.getComment_count()));
        tvPraiseCount.setText(String.valueOf(trendBean.getPraise_count()));
        List<String> pictures = trendBean.getPic_rsurls();
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
                        float scale = DisplayUtils.dip2px(TrendCommentsActivity.this, 180) * 1F / height;

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

        listView.setAdapter(adapter);
        listView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
        replyTrendContent = "";
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onTrendSuccess(TrendDetailBean trendDetail) {
        titleBar.setTitle(trendDetail.getPublisher_name());

        this.trendDetail = trendDetail;
        datas.clear();
        datas.addAll(trendDetail.getComments());
        adapter.notifyDataSetChanged();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Map<String, Object> replyTrendMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trend_id", id);
        map.put("content", replyTrendContent);
        return map;
    }

    @Override
    public void onReplyTrendSuccess(ReplyTrendBean replyTrendBean) {
        TrendDetailBean.Comment comment = new TrendDetailBean.Comment();
        comment.setComment_time(replyTrendBean.getCreateTime());
        comment.setContent(replyTrendBean.getContent());
        comment.setMember_name(replyTrendBean.getMemberName());
        datas.add(comment);
        adapter.notifyDataSetChanged();
        tvCommentCount.setText(String.valueOf(Integer.parseInt(tvCommentCount.getText().toString()) + 1));
        replyTrendContent = "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_comment:
                InputPopupWindow inputPopupWindow = new InputPopupWindow(this, new InputPopupWindow.OnInputListener() {
                    @Override
                    public void onInput(String text) {
                        replyTrendContent = text;
                        trendCommentsPresenter.replyTrend();
                    }
                });
                inputPopupWindow.showWindow();
                break;
        }
    }
}
