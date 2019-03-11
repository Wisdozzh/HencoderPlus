package com.smilecampus.zytec.hencoderplus07.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.Utils;

public class SportsView1 extends View {
    public static final float RING_WIDTH = Utils.dp2px(20);
    public static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect rect = new Rect();
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportsView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(50));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf"));
        paint.getFontMetrics(fontMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth()/2, getHeight()/2, RADIUS, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(HIGHLIGHT_COLOR);
        canvas.drawArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                -90, 250, false, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        //第一种方案 有缺陷 如果字体变化的话 容易跳动
//        paint.getTextBounds("abab", 0, "abab".length(), rect);
//        int offset = (rect.bottom + rect.top) / 2;
        //第二种方案
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("abab", getWidth()/2, getHeight()/2 - offset, paint);
    }
}
