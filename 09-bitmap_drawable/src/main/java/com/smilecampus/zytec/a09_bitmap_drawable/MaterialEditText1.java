package com.smilecampus.zytec.a09_bitmap_drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

public class MaterialEditText1 extends AppCompatEditText {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float TEXT_SIZE = Utils.dpToPixel(12);
    private static final float TEXT_MARGIN = Utils.dpToPixel(8);
    public static final int TEXT_VERTICAL_OFFSET = (int) Utils.dpToPixel(22);
    public static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dpToPixel(5);
    private static final int TEXT_ANIMATION_OFFSET = (int) Utils.dpToPixel(16);

    private boolean floatingLabelShown;
    float floatingLabelFraction;
    ObjectAnimator animator;

    private boolean useFloatingLabel;
    Rect backgroundPadding = new Rect();

    public MaterialEditText1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText1);
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText1_useFloatingLabel, true);
        typedArray.recycle();

        paint.setTextSize(TEXT_SIZE);
        paint.setColor(Color.RED);
        onUseFloatingLabelChanged();
        //设置padding 增加EditTex的高度增加
//        setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
//        if (useFloatingLabel) {
//            setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
//        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (useFloatingLabel) {
                    if (floatingLabelShown && TextUtils.isEmpty(s)) {
                        getAnimator().reverse();
                        floatingLabelShown = false;
                    } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                        getAnimator().start();
                        floatingLabelShown = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onUseFloatingLabelChanged() {
        getBackground().getPadding(backgroundPadding);
        if (useFloatingLabel) {
            setPadding(getPaddingLeft(), (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(), getPaddingBottom());
        }
    }

    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText1.this, "floatingLabelFraction", 0, 1);
        }
        return animator;
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel;
            //重新测量 但是只是测量 没有进行重新的
//            requestLayout();
//            if (useFloatingLabel) {
//                setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
//            } else {
//                setPadding(getPaddingLeft(), (int) (getPaddingTop() - TEXT_SIZE - TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
//            }
//            getBackground().getPadding(backgroundPadding);
//            if (useFloatingLabel) {
//                setPadding(getPaddingLeft(), (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
//            } else {
//                setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(), getPaddingBottom());
//            }
            onUseFloatingLabelChanged();
        }

    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + extraOffset, paint);
    }
}
