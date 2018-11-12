package com.smilecampus.zytec.a10_layout.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //父view算出来的
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对自己的期望尺寸
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int size = Math.max(measureHeight, measureWidth);
        //然后存储一下
        setMeasuredDimension(size, size);

    }
}
