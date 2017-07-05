package com.mango.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mango.R;

public class TitleBar extends FrameLayout implements View.OnClickListener {

    ImageButton ibLeft;
    ImageButton ibRight;
    ImageButton ibSecondRight;
    TextView tvRight;
    TextView tvTitle;
    TextView tvLeft;
    OnTitleBarClickListener onTitleBarClickListener;
    boolean leftBack = true;
    Activity activity;
    boolean tvLeftBack;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.bar_title, this, true);
        ibLeft = (ImageButton) root.findViewById(R.id.ib_left);
        ibRight = (ImageButton) root.findViewById(R.id.ib_right);
        ibSecondRight = (ImageButton) root.findViewById(R.id.ib_second_right);
        tvTitle = (TextView) root.findViewById(R.id.tv_title);
        tvLeft = (TextView) root.findViewById(R.id.tv_left);
        tvRight = (TextView) root.findViewById(R.id.tv_right);

        tvRight.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        ibLeft.setOnClickListener(this);
        ibRight.setOnClickListener(this);
        ibSecondRight.setOnClickListener(this);
    }

    public void setTitle(int resId){
        tvTitle.setText(getResources().getString(resId));
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setLeftText(int resId){
        if(View.GONE == tvLeft.getVisibility()){
            tvLeft.setVisibility(View.VISIBLE);
        }
        tvLeft.setText(getResources().getString(resId));
    }

    public void setRightText(int resId){
        if(View.GONE == tvRight.getVisibility()){
            tvRight.setVisibility(View.VISIBLE);
        }
        tvRight.setText(getResources().getString(resId));
    }

    public void setLeftText(String title){
        if(View.GONE == tvLeft.getVisibility()){
            tvLeft.setVisibility(View.VISIBLE);
        }
        tvLeft.setText(title);
    }

    public void setRightText(String title){
        if(View.GONE == tvRight.getVisibility()){
            tvRight.setVisibility(View.VISIBLE);
        }
        tvRight.setText(title);
    }

    public void setLeftBtnIcon(int resId){
        ibLeft.setImageResource(resId);
    }

    public void setLeftBtnIconVisibility(int left){
        ibLeft.setVisibility(left);
    }

    public void setRightBtnIcon(int resId){
        if(View.GONE == ibRight.getVisibility()){
            ibRight.setVisibility(View.VISIBLE);
        }
        ibRight.setImageResource(resId);
    }

    public void setSecondRightBtnIcon(int resId){
        if(View.GONE == ibSecondRight.getVisibility()){
            ibSecondRight.setVisibility(View.VISIBLE);
        }
        ibSecondRight.setImageResource(resId);
    }

    public void setRightBtnVisibility(int right, int secondRight){
        if(View.GONE == ibRight.getVisibility()){
            ibRight.setVisibility(View.VISIBLE);
        }
        ibRight.setVisibility(right);
        if(View.GONE == ibSecondRight.getVisibility()){
            ibSecondRight.setVisibility(View.VISIBLE);
        }
        ibSecondRight.setVisibility(secondRight);
    }

    @Override
    public void onClick(View v) {
        if(leftBack && v.getId() == R.id.ib_left){
            finishActivity();
        } else if(tvLeftBack && v.getId() == R.id.tv_left){
            finishActivity();
        } else if (onTitleBarClickListener != null){
            onTitleBarClickListener.onTitleButtonClick(v);
        }
    }

    private void finishActivity(){
        if(activity != null && !activity.isFinishing()){
            activity.finish();
        }
    }

    public interface OnTitleBarClickListener {
        void onTitleButtonClick(View view);
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    public void setLeftBack(boolean leftBack) {
        this.leftBack = leftBack;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setTvLeftBack(boolean tvLeftBack) {
        this.tvLeftBack = tvLeftBack;
    }
}
