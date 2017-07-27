package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.com.mangopi.android.util.DisplayUtils;

/**
 * @author 蒋先明
 * @date 2017/7/3
 */
public class RedPointView extends View {

    Paint paint;
    int radius;

    public RedPointView(Context context) {
        this(context, null);
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        radius = DisplayUtils.dip2px(getContext(), 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2, getHeight()/2, radius, paint);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        postInvalidate();
    }
}
