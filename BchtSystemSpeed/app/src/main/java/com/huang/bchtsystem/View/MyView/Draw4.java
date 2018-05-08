package com.huang.bchtsystem.View.MyView;

/**
 * Created by admin on 2017/8/29.
 */

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.huang.bchtsystem.Util.MyPreference;

public class Draw4 extends View {


    private float upX;
    private float upY;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;
    private Paint paint = null;
    private int num;
    public Draw4(Context context,AttributeSet attrs) {
        // TODO Auto-generated constructor stub
        super(context,attrs);
    }

    public Draw4(Context context,float startX,float startY,float endX,float endY,int num) {
        super(context);
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Style.STROKE);
        this.num = num;
        this.downX = startX;
        this.downY = startY;
        this.upX = endX;
        this.upY =endY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(downX, downY, upX, upY, paint);
    }

    private void initData()
    {
        if (num == 0) {
            downX = MyPreference.getInstance(getContext()).getDRAW_X();
            downY = MyPreference.getInstance(getContext()).getDRAW_Y();
            upX = MyPreference.getInstance(getContext()).getDRAW_X1();
            upY = MyPreference.getInstance(getContext()).getDRAW_Y1();
        }else if (num == 1) {
            downX = MyPreference.getInstance(getContext()).getDRAW_X1();
            downY = MyPreference.getInstance(getContext()).getDRAW_Y1();
            upX = MyPreference.getInstance(getContext()).getDRAW_X2();
            upY = MyPreference.getInstance(getContext()).getDRAW_Y2();
        }else if (num == 2) {
            downX = MyPreference.getInstance(getContext()).getDRAW_X2();
            downY = MyPreference.getInstance(getContext()).getDRAW_Y2();
            upX = MyPreference.getInstance(getContext()).getDRAW_X3();
            upY = MyPreference.getInstance(getContext()).getDRAW_Y3();
        }else if (num == 3) {
            downX = MyPreference.getInstance(getContext()).getDRAW_X3();
            downY = MyPreference.getInstance(getContext()).getDRAW_Y3();
            upX = MyPreference.getInstance(getContext()).getDRAW_X();
            upY = MyPreference.getInstance(getContext()).getDRAW_Y();
        }
//		downX = MyPreference.getInstance(getContext()).getDRAW_X2();
//		downY = MyPreference.getInstance(getContext()).getDRAW_Y2();
//		upX = MyPreference.getInstance(getContext()).getDRAW_X3();
//		upY = MyPreference.getInstance(getContext()).getDRAW_Y3();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //检测手指落下的动作
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (num == 0) {
                    downX = event.getX();
                    downY = event.getY();
                    MyPreference.getInstance(getContext()).SetDRAW_X(event.getX());
                    MyPreference.getInstance(getContext()).SetDRAW_Y(event.getY());
                }else if (num == 1) {
                    downX = MyPreference.getInstance(getContext()).getDRAW_X1();
                    downY = MyPreference.getInstance(getContext()).getDRAW_Y1();
                }else if (num == 2) {
                    downX = MyPreference.getInstance(getContext()).getDRAW_X2();
                    downY = MyPreference.getInstance(getContext()).getDRAW_Y2();
                }else if (num == 3) {
                    downX = MyPreference.getInstance(getContext()).getDRAW_X3();
                    downY = MyPreference.getInstance(getContext()).getDRAW_Y3();
                }

            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();
            case MotionEvent.ACTION_UP:
                if (num == 0) {
                    upX = event.getX();
                    upY = event.getY();
                    MyPreference.getInstance(getContext()).SetDRAW_X1(event.getX());
                    MyPreference.getInstance(getContext()).SetDRAW_Y1(event.getY());
                }else if (num == 1) {
                    upX = event.getX();
                    upY = event.getY();
                    MyPreference.getInstance(getContext()).SetDRAW_X2(event.getX());
                    MyPreference.getInstance(getContext()).SetDRAW_Y2(event.getY());
                }else if (num == 2) {
                    upX = event.getX();
                    upY = event.getY();
                    MyPreference.getInstance(getContext()).SetDRAW_X3(event.getX());
                    MyPreference.getInstance(getContext()).SetDRAW_Y3(event.getY());
                }else if (num == 3) {
                    upX = MyPreference.getInstance(getContext()).getDRAW_X();
                    upY = MyPreference.getInstance(getContext()).getDRAW_Y();
                }
        }
        invalidate();//使绘制动画生效
        return true;
    }


}