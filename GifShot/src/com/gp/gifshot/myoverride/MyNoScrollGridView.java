package com.gp.gifshot.myoverride;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
  
public class MyNoScrollGridView extends GridView {  
  
    public MyNoScrollGridView(Context context) {  
        super(context);  
    }  
  
    public MyNoScrollGridView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public MyNoScrollGridView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,        
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
  
    // 失去滑动属性
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        if(ev.getAction() == MotionEvent.ACTION_MOVE){     
            return true;   
        }   
        return super.dispatchTouchEvent(ev);  
    }  
      
}  
