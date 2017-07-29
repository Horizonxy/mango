package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;

public class SuccessActivity extends BaseTitleBarActivity {

    @Bind(R.id.tv_text)
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        String title = getIntent().getStringExtra(Constants.BUNDLE_TITLE);
        String text = getIntent().getStringExtra(Constants.BUNDLE_TEXT);

        titleBar.setTitle(title);
        tvText.setText(text);
    }

    @OnClick(R.id.btn_back)
    void onBackClick(View v){
        finish();
    }
}
