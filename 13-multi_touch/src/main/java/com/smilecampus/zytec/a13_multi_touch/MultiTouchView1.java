package com.smilecampus.zytec.a13_multi_touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * Description 多点触控 接力型
 * Author Genise
 * Date 2018/11/19 22:11
 */
public class MultiTouchView1 extends View {
    public static final float IMAGE_WIDTH = Utils.dpToPixel(200);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float downX;
    float downY;
    float originalOffsetX;
    float originalOffsetY;
    float offsetX;
    float offsetY;

    int trackingPointerId;
    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int)IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //拿到第0根手指的id
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = originalOffsetX + event.getX(index) - downX;
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                //拿到我按下手指的id  getActionIndex:获取 你是那个手指 抬起 或着按下
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointId = event.getPointerId(actionIndex);
                //判断当前抬起的是不是活跃的
                if (pointId == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    //拿到我按下手指的id  getActionIndex:获取 你是那个手指 抬起 或着按下
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(actionIndex);
                    downY = event.getY(actionIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                    break;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
