package com.smilecampus.zytec.hencoderplus07.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.R;
import com.smilecampus.zytec.hencoderplus07.Utils;

public class CameraView1 extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    Bitmap bitmap;
    public CameraView1(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar(600);
        camera.rotateX(45);
        camera.setLocation(0, 0, -6 * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(100 + 600/2, 100 + 600/2);
        canvas.rotate(-20);
        canvas.clipRect(-600, -600, 600, 0);
        canvas.rotate(20);
        canvas.translate(- (100 + 600/2), -(100 + 600/2));
        canvas.drawBitmap(bitmap, 100, 100, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(100 + 600/2, 100 + 600/2);
        canvas.rotate(-20);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-600, 0, 600, 600);
        canvas.rotate(20);
        canvas.translate(- (100 + 600/2), -(100 + 600/2));
        canvas.drawBitmap(bitmap, 100, 100, paint);
        canvas.restore();

    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }
}
