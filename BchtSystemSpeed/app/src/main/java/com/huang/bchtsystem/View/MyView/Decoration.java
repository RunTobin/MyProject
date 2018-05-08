package com.huang.bchtsystem.View.MyView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.huang.bchtsystem.MyApplication;
import com.huang.bchtsystem.R;


/**
 * Created by Administrator on 2016/7/29.
 */
public class Decoration extends RecyclerView.ItemDecoration {

    private int decorationHeight = 2;
    private int paddingLefAndRight = 0;

    private Paint paint;

    public Decoration() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setColorRes(int colorRes) {
        paint.setColor(MyApplication.getAppContext().getResources().getColor(colorRes));
    }

    public void setDecorationHeight(int decorationHeight) {
        this.decorationHeight = decorationHeight;
    }

    public void setPaddingLefAndRight(int paddingLefAndRight) {
        this.paddingLefAndRight = paddingLefAndRight;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left, right, top, bottom;
        left = parent.getPaddingLeft() + paddingLefAndRight;
        right = parent.getMeasuredWidth() - parent.getPaddingRight() - paddingLefAndRight;

        for (int i = 0; i < parent.getChildCount(); i++) {

            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getBottom() + params.bottomMargin;
            bottom = top + decorationHeight;
            c.drawRect(left, top, right, bottom, paint);
        }
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        int left, top, right, bottom;
        left = view.getPaddingLeft();
        top = view.getPaddingTop();
        right = view.getMeasuredWidth() - view.getPaddingRight();
        bottom = view.getMeasuredHeight() - view.getPaddingBottom();
        outRect.set(left, top, right, bottom + decorationHeight);
        super.getItemOffsets(outRect, view, parent, state);
    }
}
