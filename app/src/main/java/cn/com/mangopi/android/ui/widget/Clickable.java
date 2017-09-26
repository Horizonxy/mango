package cn.com.mangopi.android.ui.widget;

import android.text.style.ClickableSpan;
import android.view.View;

public class Clickable extends ClickableSpan implements View.OnClickListener {
    private final View.OnClickListener mListener;

    public Clickable(View.OnClickListener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v);
    }
}