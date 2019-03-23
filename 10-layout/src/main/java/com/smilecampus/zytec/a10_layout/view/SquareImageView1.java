package com.smilecampus.zytec.a10_layout.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquareImageView1 extends AppCompatImageView {

    public SquareImageView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int size = Math.max(measureWidth, measureHeight);
        setMeasuredDimension(size, size);
    }

    //    @Override
//    public void layout(int l, int t, int r, int b) {
//        int width = r - l;
//        int height = b - t;
//        int squareWidth = Math.max(width, height);
//        super.layout(l, t, l + squareWidth, t + squareWidth);
//    }
}
