package com.smilecampus.zytec.hencoderplus06.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus06.R;
import com.smilecampus.zytec.hencoderplus06.Utils;

public class AvatarView1 extends View {
    public static final float WIDTH = Utils.dp2px(300);
    public static final float PADDING = Utils.dp2px(50);
    public static final float EDGE_PADDING = Utils.dp2px(10);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF rectF = new RectF();
    Bitmap bitmap;

    public AvatarView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar((int) WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#ffff00"));
        canvas.drawOval(rectF, paint);
        paint.setColor(Color.parseColor("#000000"));
        //由于我们绘制之前 有一个View已经被绘制了 所以使用离屏缓冲的形式
        int saved = canvas.saveLayer(rectF, paint);
        canvas.drawOval(PADDING + EDGE_PADDING, PADDING + EDGE_PADDING, PADDING + WIDTH - EDGE_PADDING, PADDING + WIDTH - EDGE_PADDING, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, Utils.dp2px(50), Utils.dp2px(50), paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
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
