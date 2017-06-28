package com.mango.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.FrameLayout;

import com.mango.R;
import com.mango.ui.widget.TitleBar;

import butterknife.ButterKnife;

public class BaseTitleBarActivity extends BaseActivity {

    TitleBar titleBar;
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View root = getLayoutInflater().inflate(R.layout.activity_base_title_bar, null ,false);
        flContent = (FrameLayout) root.findViewById(R.id.fl_content);
        getLayoutInflater().inflate(layoutResID, flContent, true);
        titleBar = (TitleBar) root.findViewById(R.id.title_bar);
        titleBar.setActivity(this);
        setContentView(root);

        ButterKnife.bind(this);
    }



}
