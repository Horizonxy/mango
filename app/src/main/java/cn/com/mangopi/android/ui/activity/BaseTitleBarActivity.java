package cn.com.mangopi.android.ui.activity;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.TitleBar;

public class BaseTitleBarActivity extends BaseActivity {

    TitleBar titleBar;
    FrameLayout flContent;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View root = getLayoutInflater().inflate(R.layout.activity_base_title_bar, null ,false);
        flContent = (FrameLayout) root.findViewById(R.id.fl_content);
        getLayoutInflater().inflate(layoutResID, flContent, true);
        titleBar = (TitleBar) root.findViewById(R.id.title_bar);
        titleBar.setActivity(this);
        titleBar.setLeftBtnIcon(R.drawable.back);

        setContentView(root);

        ButterKnife.bind(this);
    }



}
