package com.huang.bchtsystem.View.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by admin on 2017/6/6.
 */

public class MyView extends View{
    private float x = 0;
    private float y = 0;
    private float speedx = 50;
    private float speedy = 50;
    Context m_context;
    public MyView(Context context) {
        super(context);
        m_context = context ;
    }
    //重写OnDraw（）函数，在每次重绘时自主实现绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setShadowLayer(10,15,15,Color.GREEN);//设置阴影

        canvas.drawCircle(190,200,150,paint);
        canvas.drawRect(200,400,400,600,paint);//直接构造 
    }
}
