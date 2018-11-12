package com.smilecampus.zytec.hencoderplus06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus06.Utils;

public class PieChart extends View {
    public static final int RADIUS = (int) Utils.dp2px(150);
    public static final int LENGTH = (int) Utils.dp2px(20);
    public static final int PULL_OUT_INDEX = 2;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bounds = new RectF();
    int[] angles = {60, 100, 80, 120};
    int[] colors = {Color.parseColor("#ff0000"),
                    Color.parseColor("#00ff00"),
                    Color.parseColor("#0000ff"),
                    Color.parseColor("#ff00ff")};

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS,
                getWidth()/2 + RADIUS,getHeight()/2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == PULL_OUT_INDEX) {
                canvas.translate((float)Math.cos(Math.toRadians(currentAngle + angles[i]/ 2)) * LENGTH,
                        (float)Math.sin(Math.toRadians(currentAngle + angles[i]/2)) * LENGTH);
            }
            canvas.drawArc(bounds,currentAngle,angles[i],true,paint);
            canvas.restore();
            currentAngle += angles[i];
        }
    }
}
