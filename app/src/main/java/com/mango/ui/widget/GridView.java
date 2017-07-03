package com.mango.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class GridView extends android.widget.GridView {
	  
    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);   
    }   
  
    public GridView(Context context) {
        super(context);   
    }   
  
    public GridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);   
    }   
  
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
  
        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }   
} 