package com.genise.zytec.a15_drag_nestedscroll.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DragHelperGridView extends ViewGroup {
    public static final int COLUMNS = 2;
    public static final int ROWS = 3;

    ViewDragHelper dragHelper = null;

    public DragHelperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, new DragCallback());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //和onInterceptTouchEvent 功能一样 只是名字不同
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //和onTouchEvent 功能一样 只是名字不同
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {
        float captureLeft;
        float captureTop;

        //尝试抓住view 是不是抓住view
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                View captureView = dragHelper.getCapturedView();
                if (captureView != null) {
                    captureView.setElevation(captureView.getElevation() - 1);
                }
            }
        }

        //夹紧 有个轨道 限制你的 拖拽以左侧的垂直线为主 默认是0 是拖不动的
        //如果返回0 全部从圆点开始
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            //初始化
            capturedChild.setElevation(capturedChild.getElevation() + 1);
            captureLeft = capturedChild.getLeft();
            captureTop = capturedChild.getTop();
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {

        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            //手动去放置
            dragHelper.settleCapturedViewAt((int) captureLeft, (int) captureTop);
            //不断的重新绘制 onDraw中 会不断的自动调用computeScroll
            postInvalidateOnAnimation();
        }
    }
}
