package com.smilecampus.zytec.hencoderplus07.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.Utils;

public class CameraView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        camera.rotateX(45);
        //z轴 代表着英尺 1英尺 = 8dp Utils用来做兼容性配置
        camera.setLocation(0, 0, Utils.getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(Utils.getAvatar(getResources(),600),100,100,paint);

        //绘制上半部分
        canvas.save();
        canvas.translate(100 + 600 / 2, 100 + 600 / 2);
        canvas.rotate(-20);
        canvas.clipRect(-600, -600, 600, 0);
        canvas.rotate(20);
        canvas.translate(- (100 + 600 / 2), - (100 + 600/2));
        canvas.drawBitmap(Utils.getAvatar(getResources(),600),100,100,paint);
        canvas.restore();

        //绘制下班部分
        // 1、将其原点移动到圆心处
        // 2、然后再旋转
        // 3、最后再裁切
        // 4、做三维变换
        // 5、再旋转回来
        // 6、再移回来
        canvas.save();
        canvas.translate(100 + 600/2,100 + 600/2);
        canvas.rotate(-20);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-600,0,600,600);
        canvas.rotate(20);
        canvas.translate(- (100 + 600/2),-(100 + 600/2));
        canvas.drawBitmap(Utils.getAvatar(getResources(),600),100,100,paint);
        canvas.restore();
    }
}
