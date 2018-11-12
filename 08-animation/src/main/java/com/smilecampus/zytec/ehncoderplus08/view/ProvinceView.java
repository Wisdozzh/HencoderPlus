package com.smilecampus.zytec.ehncoderplus08.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.ehncoderplus08.utils.Utils;

public class ProvinceView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    String province = "北京市";
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(50));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.getFontMetrics(fontMetrics);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = getWidth() / 2;
        float y = getHeight()/2  - (fontMetrics.descent + fontMetrics.ascent) / 2;
        canvas.drawText(province, x, y, paint);
    }
}
