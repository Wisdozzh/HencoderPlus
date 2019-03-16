package com.smilecampus.zytec.ehncoderplus08.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.ehncoderplus08.utils.Utils;

public class PointView1 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Point point = new Point(0, 0);

    public PointView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStrokeWidth(Utils.dp2px(15));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(point.x, point.y, paint);
    }
}
