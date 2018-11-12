package com.smilecampus.zytec.a09_bitmap_drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

public class MaterialEditText extends AppCompatEditText {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float TEXT_SIZE = Utils.dpToPixel(12);
    public static final int TEXT_VERTICAL_OFFSET = (int) Utils.dpToPixel(22);
    public static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dpToPixel(5);
    private static final int TEXT_ANIMATION_OFFSET = (int) Utils.dpToPixel(16);

    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    float floatingLabelFraction;
    ObjectAnimator animator;
    boolean floatingLabelShown = true;//判断之前是显示

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(TEXT_SIZE);
        paint.getFontMetrics(fontMetrics);
        paint.setColor(Color.RED);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (floatingLabelShown && TextUtils.isEmpty(s)) {
                    getAnimator().reverse();
                    floatingLabelShown = false;
                } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                    getAnimator().start();
                    floatingLabelShown = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    public ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0, 1);
        }
        return animator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha((int) (0xff * floatingLabelFraction));

        float offset = fontMetrics.top;
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, offset + TEXT_VERTICAL_OFFSET + extraOffset, paint);
    }
}
