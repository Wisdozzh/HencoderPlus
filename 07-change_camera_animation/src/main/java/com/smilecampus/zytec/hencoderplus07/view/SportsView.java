package com.smilecampus.zytec.hencoderplus07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.Utils;

public class SportsView extends View {

    public static final float RING_WIDTH = Utils.dp2px(20);
    public static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect bounds = new Rect();
    Paint.FontMetrics metrics = new Paint.FontMetrics();

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(50));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf"));
        paint.getFontMetrics(metrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(RING_WIDTH);
        paint.setColor(CIRCLE_COLOR);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        //绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                        getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                -90,300,false,paint);

        //绘制文字 文字居中 第一种方式 bounds 文字非常的居中，但是如果文字变化的时候 会有跳动的感觉  如果bbbb变成aaa bounds的范围会变化 所以看起来会有跳动的感觉
//        paint.setStyle(Paint.Style.FILL);
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.getTextBounds("abcd", 0, "abcd".length(), bounds);
//        int offset = (bounds.top + bounds.bottom) / 2;
//        canvas.drawText("adcd",getWidth()/2,getHeight()/2 - offset, paint);

        //绘制文字 文字居中 第二种方式 更改文字 不会有跳动的感觉 使用descent和ascent 最多情况下的文字中心 bbbb变成aaa 他俩不会变
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        float offset = (metrics.descent + metrics.ascent) / 2;
        canvas.drawText("凯哥发红包",getWidth()/2,getHeight()/2 - offset, paint);
    }
}
