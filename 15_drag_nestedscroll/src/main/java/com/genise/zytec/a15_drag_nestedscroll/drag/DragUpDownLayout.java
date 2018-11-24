package com.genise.zytec.a15_drag_nestedscroll.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.genise.zytec.a15_drag_nestedscroll.R;
/**
 * Description 默认 能钳住 只能纵向滑动
 * Author Genise
 * Date 2018/11/24 17:09
 */
public class DragUpDownLayout extends FrameLayout {
    View view;
    ViewDragHelper dragHelper;
    ViewDragHelper.Callback dragListener = new DragListener();
    ViewConfiguration viewConfiguration;

    public DragUpDownLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        dragHelper = ViewDragHelper.create(this, dragListener);
        viewConfiguration = ViewConfiguration.get(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = findViewById(R.id.view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    class DragListener extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int i) {
            return child == view;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            //如果滑动速度特别慢 并且偏下那个方向
            if (Math.abs(yvel) > viewConfiguration.getScaledMinimumFlingVelocity()) {
                if (yvel > 0) {
                    //相当于 scroller的fling
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                } else {
                    dragHelper.settleCapturedViewAt(0, 0);
                }
            } else {
                if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) {
                    dragHelper.settleCapturedViewAt(0, 0);
                } else {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                }
            }
            postInvalidateOnAnimation();
        }
    }
}
