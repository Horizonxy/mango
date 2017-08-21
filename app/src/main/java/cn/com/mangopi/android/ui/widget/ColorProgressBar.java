package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import cn.com.mangopi.android.R;

public class ColorProgressBar extends ProgressBar {

    public ColorProgressBar(Context context) {
        super(context);
        registerChange();
    }

    public ColorProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        registerChange();
    }

    public ColorProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        registerChange();
    }

    protected void registerChange() {
        getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.color_ffb900), PorterDuff.Mode.SRC_IN);
    }

}
