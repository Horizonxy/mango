package cn.com.mangopi.android.ui.widget;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;

public class NoUnderlineSpan extends UnderlineSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        //设置可点击文本的字体颜色
        ds.setColor(Application.application.getResources().getColor(R.color.color_ffb900));
        ds.setUnderlineText(false);

    }
}