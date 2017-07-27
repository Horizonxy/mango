package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.BulletinBean;
import cn.com.mangopi.android.util.DisplayUtils;

public class VerticalTextview extends ViewSwitcher implements ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 0;
    private static final int FLAG_STOP_AUTO_SCROLL = 1;

    private float mTextSize = 16 ;
    private int mPadding = 5;
    private int textColor = Color.BLACK;

    /**
     * @param textSize 字号
     * @param padding 内边距
     * @param textColor 字体颜色
     */
    public void setText(float textSize,int padding,int textColor) {
        mTextSize = textSize;
        mPadding = padding;
        this.textColor = textColor;
    }

    private OnItemClickListener itemClickListener;
    private Context mContext;
    private int currentId = -1;
    private List textList;
    private Handler handler;

    public VerticalTextview(Context context) {
        this(context, null);
        mContext = context;
    }

    public VerticalTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        textList = new ArrayList<>();
    }

    public void setAnimTime(long animDuration) {
        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, DisplayUtils.dip2px(getContext(), 16), 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        AlphaAnimation inAlpha = new AlphaAnimation(0f, 1);
        inAlpha.setDuration(animDuration);
        Animation out = new TranslateAnimation(0, 0, 0, -DisplayUtils.dip2px(getContext(), 16));
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        AlphaAnimation outAlpha = new AlphaAnimation(1, 0f);
        outAlpha.setDuration(animDuration);

        AnimationSet inSet = new AnimationSet(true);
        inSet.addAnimation(in);
        inSet.addAnimation(inAlpha);

        AnimationSet outSet = new AnimationSet(true);
        outSet.addAnimation(out);
        outSet.addAnimation(outAlpha);

        setInAnimation(inSet);
        setOutAnimation(outSet);
    }

    /**
     * 间隔时间
     * @param time
     */
    public void setTextStillTime(final long time){
       handler =new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if (textList.size() > 0) {
                            currentId++;
                            BulletinBean contentVo = (BulletinBean) textList.get(currentId % textList.size());
                            TextView textView = (TextView) getNextView();
                            textView.setText(contentVo.getTitle());
                            showNext();
                        }
                        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL,time);
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                }
            }
        };
    }
    /**
     * 设置数据源
     * @param titles
     */
    public void setTextList(List titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
    }

    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(14);
        textView.setTextColor(getResources().getColor(R.color.color_333333));
        textView.setGravity(Gravity.CENTER_VERTICAL);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(params);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                    itemClickListener.onItemClick(textList.get(currentId % textList.size()), currentId % textList.size());
                }
            }
        });

        return textView;
    }

    /**
     * 设置点击事件监听
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * 点击回调
         * @param position 当前点击ID
         */
        void onItemClick(Object contentVo, int position);
    }

}