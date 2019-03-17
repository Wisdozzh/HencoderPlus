package com.smilecampus.zytec.a09_bitmap_drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawableView1 extends View {
    Drawable drawable;

    public DrawableView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        drawable = new MeshDrawable1();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }
}
