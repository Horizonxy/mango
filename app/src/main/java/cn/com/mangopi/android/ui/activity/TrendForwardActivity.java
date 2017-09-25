package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.TrendBean;
import cn.com.mangopi.android.ui.widget.RoundImageView;
import cn.com.mangopi.android.ui.widget.TitleBar;

public class TrendForwardActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trenf_forward);

        trendBean = (TrendBean) getIntent().getSerializableExtra(Constants.BUNDLE_DATA);
        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.forward);
        titleBar.setRightText(R.string.publish);
        tvContentNum.setText(String.format(getString(R.string.input_num), String.valueOf(1000)));

        tvTrendName.setText(trendBean.getPublisher_name());
        tvTrendContent.setText(trendBean.getContent());
        Application.application.getImageLoader().displayImage(trendBean.getAvatar_rsurl(), ivAvatar, Application.application.getDefaultOptions());
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:

                break;
        }
    }
}
