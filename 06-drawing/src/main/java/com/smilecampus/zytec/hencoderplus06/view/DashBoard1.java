package com.smilecampus.zytec.hencoderplus06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus06.Utils;

public class DashBoard1 extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final int ANGLE = 120;
    private static final float RADIUS = Utils.dp2px(150);
    private static final float LENGTH = Utils.dp2px(100);

    //画弧线 小矩形的虚线
    Path dash = new Path();
    PathDashPathEffect effect;

    public DashBoard1(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));

        //一个矩形
        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);

        //算弧度周长
        Path arc = new Path();
        arc.addArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS, getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                90 + ANGLE/2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        float length = pathMeasure.getLength();

        effect = new PathDashPathEffect(dash, (length - Utils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS, getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                90 + ANGLE/2, 360 - ANGLE, false, paint);

        //设置为虚线
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth()/2 - RADIUS, getHeight()/2 - RADIUS, getWidth()/2 + RADIUS, getHeight()/2 + RADIUS,
                90 + ANGLE/2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);

        //画一条线
        canvas.drawLine(getWidth()/2, getHeight()/2,
                (float)Math.cos(Math.toRadians(getAngleFromMark(18))) * LENGTH + getWidth()/2,
                (float)Math.sin(Math.toRadians(getAngleFromMark(18))) * LENGTH + getHeight()/2,
                paint);
    }

    int getAngleFromMark(int mark) {
        return 90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark;
    }
}
