package com.smilecampus.zytec.a14_view_pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

public class TwoPager extends ViewGroup {
    float downX;
    float downY;
    float downScrollX;
    boolean scrolling;
    float maxVelocity;
    float minVelocity;
    OverScroller overScroller;
    ViewConfiguration viewConfiguration;
    VelocityTracker velocityTracker = VelocityTracker.obtain();

    public TwoPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        overScroller = new OverScroller(context);
        viewConfiguration = ViewConfiguration.get(context);
        maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        minVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    }

    //布局代码


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  非常少用的 把所有的子view 全都测量了
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历我的所有的子view的  传递实际的宽高 和位置的
        int childLeft = 0;
        int childTop = 0;
        int childRight = getWidth();
        int childBottom = getHeight();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childLeft, childTop, childRight, childBottom);
            childLeft += getWidth();
            childRight += getWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //1、判断我是否需要拦截 2、一些初始状态的存储 为将来可能在onTouchEvent里用到的时候作准备
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(ev);
        boolean result = false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                scrolling = false;
                downX = ev.getX();
                downY = ev.getY();
                downScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = downX - ev.getX();
                if (!scrolling) {
                    //移动的长不长
                    if (Math.abs(dx) > viewConfiguration.getScaledPagingTouchSlop()) {
                        scrolling = true;
                        //针对你调用的方法的view 对父View发送一个命令 你不要拦截我 一般作用于嵌套两个可以滑动的view 例如里面一个viewpager 外面是scrollview
                        //手指全部抬起来的时候 就自动重置了
                        getParent().requestDisallowInterceptTouchEvent(true);
                        result = true;
                    }
                }
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                downScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = downX - event.getX() + downScrollX;
                if (dx > getWidth()) {
                    dx = getWidth();
                } else if (dx < 0){
                    dx = 0;
                }
                scrollTo((int) dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                //1000ms 定时记一下时
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                float vx = velocityTracker.getXVelocity();
                int scrollX = getScrollX();
                int targetPage;
                //如果横向的速度小于一个特定的值 我就把它定为用户手抬起来 不想滑动到下一页 希望停下来
                if (Math.abs(vx) < minVelocity) {
                    targetPage = scrollX > getWidth() / 2 ? 1 : 0;
                } else {
                    targetPage = vx < 0 ? 1 : 0;
                }
                int scrollDistance = targetPage == 1 ? (getWidth() - scrollX) : -scrollX;
                //有起始值 快快的滑动 慢慢的停下 用startScroll
                //如果快快的滑动 然后有一种撞墙的 用fling
                overScroller.startScroll(getScrollX(), 0, scrollDistance,0);
                //computeScroll会自动的调用 在draw 并且在onDraw之前调用 先计算一下
                postInvalidateOnAnimation();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.getCurrX(), overScroller.getCurrY());
            postInvalidateOnAnimation();
        }
    }
}
