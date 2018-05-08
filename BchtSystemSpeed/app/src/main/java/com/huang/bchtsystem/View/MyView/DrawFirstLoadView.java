package com.huang.bchtsystem.View.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.huang.bchtsystem.Util.MyPreference;

/**
 * Created by admin on 2017/8/25.
 */

public class DrawFirstLoadView extends View {

    private float upX;
    private float upY;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;
    private Paint paint = null;

    public DrawFirstLoadView(Context context) {
        super(context);
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        initData();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);
        canvas.drawLine(downX, downY, upX, upY, paint);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(20);
        canvas.drawText("车道线1",(upX-downX)/2+downX,(upY-downY)/2+downY,paint);
        invalidate();//重绘视图
    }
    private void initData(){
        downX =  MyPreference.getInstance(getContext()).getDRAWFRISTLINE_X();
        downY =  MyPreference.getInstance(getContext()).getDRAWFRISTLINE_Y();
        upX = MyPreference.getInstance(getContext()).getDRAWFRISTLINE_X1();
        upY = MyPreference.getInstance(getContext()).getDRAWFRISTLINE_Y1();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                MyPreference.getInstance(getContext()).SetDRAWFRISTLINE_X(downX);
                MyPreference.getInstance(getContext()).SetDRAWFRISTLINE_Y(downY);
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                MyPreference.getInstance(getContext()).SetDRAWFRISTLINE_X1(upX);
                MyPreference.getInstance(getContext()).SetDRAWFRISTLINE_Y1(upY);
        }
        invalidate();//使绘制动画生效
        return true;
    }
}
