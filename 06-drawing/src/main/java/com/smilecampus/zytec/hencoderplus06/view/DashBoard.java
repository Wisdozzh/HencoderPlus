package com.smilecampus.zytec.hencoderplus06.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus06.Utils;

public class DashBoard extends View {
    private static final int ANGLE = 120;
    private static final float RADIUS = Utils.dp2px(150);
    private static final float LENGTH = Utils.dp2px(100);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();
    //advance 要空多大距离 phase 第一个刻度空多少  拐弯的风格
    PathDashPathEffect effect;

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));
        //方向 正向 顺时针CW
        dash.addRect(0,0,Utils.dp2px(2),Utils.dp2px(10), Path.Direction.CW);


        Path arc = new Path();
        arc.addArc(getWidth()/2 - RADIUS,getHeight()/2 - RADIUS,getWidth()/2 +RADIUS,getHeight()/2 +RADIUS,
                90 + ANGLE/2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc,false);
        float length = pathMeasure.getLength();

        effect = new PathDashPathEffect(dash, (length - Utils.dp2px(2))/20, 0, PathDashPathEffect.Style.ROTATE);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(getWidth()/2 - RADIUS,getHeight()/2 - RADIUS,getWidth()/2 +RADIUS,getHeight()/2 +RADIUS,
                90 + ANGLE/2, 360 - ANGLE, false, paint);

        //画刻度
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth()/2 - RADIUS,getHeight()/2 - RADIUS,getWidth()/2 +RADIUS,getHeight()/2 +RADIUS,
                90 + ANGLE/2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);

        canvas.drawLine(getWidth()/2,getHeight()/2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth()/2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight()/2,
                paint);
    }

    int getAngleFromMark(int mark) {
        return (int) (90 + (float)ANGLE/2 + (360 - (float)ANGLE) / 20 * mark);
    }
}
