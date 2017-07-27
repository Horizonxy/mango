package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ListView extends android.widget.ListView {
	  
    public ListView(Context context) {
        // TODO Auto-generated method stub  
        super(context);  
    }  
  
    public ListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub  
        super(context, attrs);  
    }  
  
    public ListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub  
        super(context, attrs, defStyle);  
    }  
  
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        // TODO Auto-generated method stub  
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
}  
