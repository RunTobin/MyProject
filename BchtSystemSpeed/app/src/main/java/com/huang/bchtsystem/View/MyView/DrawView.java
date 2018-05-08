package com.huang.bchtsystem.View.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.huang.bchtsystem.Model.DrawData;
import com.huang.bchtsystem.Util.MyPreference;

/**
 * Created by admin on 2017/8/23.
 */

public class DrawView extends View{

    private Paint paint;

    private Canvas canvas;
    private Path path2;
    private int width,hight;
    private float start_x,start_y,start_x1,start_y1,start_x2,start_y2,start_x3,start_y3;
    private int num = 0;
    private DrawData drawData ;
    private float moveX,moveY;

    public DrawView(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
        paint = new Paint();
        canvas = new Canvas();
        path2 = new Path();
        drawData = new DrawData();
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
//        paint.reset();// 重置
        paint.setStyle(Paint.Style.STROKE);// 设置空心
        paint.setColor(Color.YELLOW);
        path2.moveTo(drawData.getStart_X(), drawData.getStart_Y());
        path2.lineTo(drawData.start_X1,  drawData.start_Y1);
        path2.lineTo(drawData.start_X2, drawData.start_Y2);
        path2.lineTo(drawData.start_X3, drawData.start_Y3);
        path2.close();// 封闭
        canvas.drawPath(path2, paint);
        path2 = new Path();
        invalidate();//重绘视图 
    }
    private void initData(){
        drawData.start_X = MyPreference.getInstance(getContext()).getDRAW_X();
        drawData.start_Y = MyPreference.getInstance(getContext()).getDRAW_Y();
        drawData.start_X1 = MyPreference.getInstance(getContext()).getDRAW_X1();
        drawData.start_Y1 = MyPreference.getInstance(getContext()).getDRAW_Y1();
        drawData.start_X2 = MyPreference.getInstance(getContext()).getDRAW_X2();
        drawData.start_Y2 = MyPreference.getInstance(getContext()).getDRAW_Y2();
        drawData.start_X3 = MyPreference.getInstance(getContext()).getDRAW_X3();
        drawData.start_Y3 = MyPreference.getInstance(getContext()).getDRAW_Y3();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //检测手指落下的动作
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(num == 0){
                drawData.setStart_X(event.getX());
                drawData.setStart_Y(event.getY());
                path2.moveTo(drawData.getStart_X(), drawData.getStart_Y());
                MyPreference.getInstance(getContext()).SetDRAW_X(event.getX());
                MyPreference.getInstance(getContext()).SetDRAW_Y(event.getY());
                num++;
            }else if (num == 1) {
                drawData.setStart_X1(event.getX());
                drawData.setStart_Y1(event.getY());
                path2.lineTo(drawData.getStart_X1(), drawData.getStart_Y1());
                MyPreference.getInstance(getContext()).SetDRAW_X1(event.getX());
                MyPreference.getInstance(getContext()).SetDRAW_Y1(event.getY());
                num++;
            }else if (num == 2) {
                drawData.setStart_X2(event.getX());
                drawData.setStart_Y2(event.getY());
                path2.lineTo(drawData.getStart_X2(), drawData.getStart_Y2());
                MyPreference.getInstance(getContext()).SetDRAW_X2(event.getX());
                MyPreference.getInstance(getContext()).SetDRAW_Y2(event.getY());
                num++;
            }else if (num == 3) {
                drawData.setStart_X3(event.getX());
                drawData.setStart_Y3(event.getY());
                path2.lineTo(drawData.getStart_X3(), drawData.getStart_Y3());
                MyPreference.getInstance(getContext()).SetDRAW_X3(event.getX());
                MyPreference.getInstance(getContext()).SetDRAW_Y3(event.getY());
                num = 0;
            }
        }
        invalidate();//使绘制动画生效
        return true;
    }

}
