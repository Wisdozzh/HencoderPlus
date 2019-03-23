package com.smilecampus.zytec.a10_layout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.a10_layout.Utils;

public class CircleView1 extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final int RADIUS = (int) Utils.dp2px(80);
    public static final int PADDING = (int) Utils.dp2px(3);

    public CircleView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (PADDING + RADIUS) * 2;
        int height = (PADDING + RADIUS) * 2;

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(PADDING + RADIUS,PADDING + RADIUS, RADIUS, paint);
    }
}
