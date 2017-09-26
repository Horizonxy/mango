package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.mcxiaoke.bus.Bus;

import butterknife.Bind;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.presenter.TrendForwardPresenter;
import cn.com.mangopi.android.ui.viewlistener.TrendForwardListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.RoundImageView;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import rx.functions.Action1;

public class TrendForwardActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener, TrendForwardListener{

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_trend_name)
    TextView tvTrendName;
    @Bind(R.id.tv_trend_content)
    TextView tvTrendContent;
    @Bind(R.id.iv_avatar)
    RoundImageView ivAvatar;
    @Bind(R.id.tv_content_num)
    TextView tvContentNum;
    TrendBean trendBean;
    TrendForwardPresenter forwardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trenf_forward);

        trendBean = (TrendBean) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);
        initView();
        forwardPresenter = new TrendForwardPresenter(this);
    }

    private void initView() {
        titleBar.setTitle(R.string.forward);
        titleBar.setRightText(R.string.publish);
        titleBar.setOnTitleBarClickListener(this);
        tvContentNum.setText(String.format(getString(R.string.input_num), String.valueOf(1000)));
        RxTextView.textChanges(etContent).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                tvContentNum.setText(String.format(getString(R.string.input_num), String.valueOf(1000 - charSequence.toString().length())));
            }
        });

        tvTrendName.setText(trendBean.getPublisher_name());
        tvTrendContent.setText(trendBean.getContent());
        Application.application.getImageLoader().displayImage(trendBean.getAvatar_rsurl(), ivAvatar, Application.application.getDefaultOptions());
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                forwardPresenter.forwardTrend();
                break;
        }
    }

    @Override
    public long getForwardId() {
        if(trendBean == null){
            return  0;
        }
        return trendBean.getId();
    }

    @Override
    public String getContent() {
        return etContent.getText().toString();
    }

    @Override
    public void onForwardSuccess() {
//        BusEvent.RefreshTrendListEvent event = new BusEvent.RefreshTrendListEvent();
//        Bus.getDefault().postSticky(event);
        AppUtils.showToast(this, "动态已转发，请耐心等待管理人员的审核");
        finish();
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }
}
