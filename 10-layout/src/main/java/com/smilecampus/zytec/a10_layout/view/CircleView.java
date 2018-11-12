package com.smilecampus.zytec.a10_layout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.a10_layout.Utils;

public class CircleView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final int RADIUS = (int) Utils.dp2px(80);
    public static final int PADDING = (int) Utils.dp2px(3);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // widthMeasureSpec期望
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (PADDING + RADIUS) * 2;
        int height = (PADDING + RADIUS) * 2;

        //算出来的高度 父view的高度  是否需要对他进行修正
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint);
    }

}
