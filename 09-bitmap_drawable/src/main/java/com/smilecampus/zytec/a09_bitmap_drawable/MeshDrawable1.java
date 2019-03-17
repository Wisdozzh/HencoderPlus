package com.smilecampus.zytec.a09_bitmap_drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MeshDrawable1 extends Drawable {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final int INTERVAL = (int) Utils.dpToPixel(20);

    @Override
    public void draw(Canvas canvas) {
        for (int i = INTERVAL; i < getBounds().right; i += INTERVAL) {
            for (int j = INTERVAL; j < getBounds().bottom; j += INTERVAL) {
                canvas.drawPoint(i, j, paint);
            }
        }
    }

    {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(Utils.dpToPixel(5));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public int getAlpha() {
        return paint.getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha() == 0 ? PixelFormat.TRANSPARENT
                : paint.getAlpha() == 0xff ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT;
    }
}
