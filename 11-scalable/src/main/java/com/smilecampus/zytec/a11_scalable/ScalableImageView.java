package com.smilecampus.zytec.a11_scalable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

/**
 * Description 双击放缩
 * Author Genise
 * Date 2018/11/10 22:36
 */
public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {
    //双击 GestureDetector GestureDetectorCompat两个是一个东西  相当于Activity 和 ActivityCompat(为了兼容性 出的一个support包，就像fragment、
    // 一旦新系统里面的新加了什么新特性 support里面都会加上的)
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    //添加一个放大系数
    private static final float OVER_SCALE_FACTOR = 1.5f;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    //初始偏移
    float originalOffsetX;//初始化 得有高度 要么在layout()、onLayout()、活着onSizeChanged()方法中初始化
    float originalOffsetY;
    //方大后的偏移
    float offsetX;
    float offsetY;
    float smallScale;
    float bigScale;
    boolean big;//当前是否是大图

    //惯性滑动 只是计算器 只记录了你有这么个运动 之后刷新自己做哦
    //Scroller 它的速度瞬间降低、fling可以添加两个参数 overX overY你可以过度的效果
    //OverScroller 速度正常、
    OverScroller overScroller;

    //添加动画
    ObjectAnimator scaleAnimator;
    float scaleFraction;//0 ~1f 0 smallScale 1 bigScale

    GestureDetectorCompat gestureDetectorCompat;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        //创建一个侦测器 还需要拦截 还需要重写onTouchEvent 调用GestureDetectorCompat的onTouchEvent()
        gestureDetectorCompat = new GestureDetectorCompat(context, this);
        //设置双击
        gestureDetectorCompat.setOnDoubleTapListener(this);

        //减少复写方法 SimpleOnGestureListener实现了 GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
        /*
        gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });
        */
        overScroller = new OverScroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //居中  int/2 有可能偏 除以2f
        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        //图片的宽高比 图片的宽高比更胖 如果是胖的图片 宽 > 高
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {//如果是高的图 高 > 宽
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //方法1 将双击方法的图片 然后拖拽 然后缩小回到原点 然后在缩放动画中添加动画监听
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
//        float scale = big ? bigScale : smallScale;
        //需要方法多少
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2);
        canvas.drawBitmap(bitmap, 0 + originalOffsetX, 0 + originalOffsetY, paint);
    }

    /**
     * 当接收到 ACTION_DOWN事件的时候 可以调用到
     * 代表是否消费该事件
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    /**==========================GestureDetector.OnGestureListener需要复写的方法==========================*/
    /**
     * 延迟显示 也就是View里面的PrePressed的延迟100ms显示
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 当单次按下 抬起
     * 返回值中 onDown 有用 其他的返回值对我们来说是没有用的
     * 是给framework层开发人员测试用的
     * 如果是长摁（500ms） 并不一定在抬起的瞬间触发该方法
     * 如果关闭长摁事件
     * GestureDetectorCompat里面的  gestureDetectorCompat.setIsLongpressEnabled(false);
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * ACTION_MOVE
     *
     * @param e1        按下的事件
     * @param e2        当前事件
     * @param distanceX 距离x 上一次event和这一次event x的距离 如果从下往上移动 x为正值 但是偏移量为负值
     * @param distanceY 距离y上一次event和这一次event y的距离
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //如果是大图 再移动
        if (big) {
            offsetX -= distanceX;
            offsetY -= distanceY;
            fitOffsets();
            invalidate();
        }
        return false;
    }

    private void fitOffsets() {
        //图片有临界值 不能大与放大图片的宽 - View的宽 / 2
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    /**
     * 长摁
     * GestureDetector GestureDetectorCompat两个的onLongPress不一样
     * GestureDetector：500ms
     * GestureDetectorCompat：600ms
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 手指快速滑动 手指在滑动的过程中抬起了 惯性滑动
     * onFling不会被反复调用的  是当你的手指抬起的一瞬间 GestureDetectorCompat看你的速度达没达到 达到onFling
     * @param e1        按下的事件
     * @param e2        当前事件
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (big) {
            /**
             * Start scrolling based on a fling gesture. The distance traveled will
             * depend on the initial velocity of the fling.
             *
             * @param startX Starting point of the scroll (X) 起始x
             * @param startY Starting point of the scroll (Y) 起始y
             * @param velocityX Initial velocity of the fling (X) measured in pixels per
             *            second. 速度x
             * @param velocityY Initial velocity of the fling (Y) measured in pixels per
             *            second 速度y
             * @param minX Minimum X value. The scroller will not scroll past this point
             *            unless overX > 0. If overfling is allowed, it will use minX as
             *            a springback boundary.
             * @param maxX Maximum X value. The scroller will not scroll past this point
             *            unless overX > 0. If overfling is allowed, it will use maxX as
             *            a springback boundary.
             * @param minY Minimum Y value. The scroller will not scroll past this point
             *            unless overY > 0. If overfling is allowed, it will use minY as
             *            a springback boundary.
             * @param maxY Maximum Y value. The scroller will not scroll past this point
             *            unless overY > 0. If overfling is allowed, it will use maxY as
             *            a springback boundary.
             * @param overX Overfling range. If > 0, horizontal overfling in either
             *            direction will be possible.
             * @param overY Overfling range. If > 0, vertical overfling in either
             *            direction will be possible.
             *
             */
            overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);
            //手动添加动画
//            for (int i = 10; i < 100; i += 10) {
//                postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refresh();
//                    }
//                }, i);
//            }
            //不像上面那么做 让run()方法里面的代码 下一帧执行
            postOnAnimation(this);
        }
        return false;
    }

    /**==========================GestureDetector.OnDoubleTapListener需要复写的方法==========================*/

    /**
     * 单击确认 用户是想单击 而不是双击（短时间触摸两次）
     * 如果你按下的时间比较长 超过我的双击判断时间 我认为你是单击
     * 如果你的手特别快 摸到就抬起来了 任何事情都不发生 等双击确认结束之后（确认不是双击） 单击事件比相应了
     * 如果你设置双击监听 还想单击监听 就用onSingleTapConfirmed方法
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击 两次摸到间隔不超过300ms
     * 如果连续触摸屏幕四次 onDoubleTap被调用两次
     * 如果手抖 我想单击 但是一不小心在屏幕蹭了一下  会被排除 （如果时间小于40ms）
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        big = !big;
        //如果当前是要放大 那缩放动画是从0-1
        if (big) {
            offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f) * bigScale / smallScale;
            offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f) * bigScale / smallScale;
            //修复边界
            fitOffsets();
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }
        //还需要重新绘制界面
//        invalidate();
        return false;
    }

    /**
     * 第二次摸到 也会被触发 当第二次摸到后 移动和抬起也会触发
     * 早期google地图 双击移动 可以三维调整
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    /**
     * ==========================缩放动画==========================
     */
    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0, 1);
        }
        return scaleAnimator;
    }

    private float getScaleFraction() {
        return scaleFraction;
    }

    private void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    /**
     * ==========================缩放动画==========================
     */

    @Override
    public void run() {
        //这个动画是否没有执行完 这个动画是否在执行中
        if (overScroller.computeScrollOffset()) {
            //overscroller 刷新一下
            overScroller.computeScrollOffset();
            offsetX = overScroller.getCurrX();
            offsetY = overScroller.getCurrY();
            invalidate();
            //也在主线程中执行 但是等到下一帧才执行
            postOnAnimation(this);
            //立即在主线程中执行 如果删除invalidate()和postOnAnimation(this); 有可能一帧执行好几次post(this)
//            post(this);
            //自动兼容 旧系统用post 新系统用postOnAnimation
            ViewCompat.postOnAnimation(this,this);
        }
    }
}
