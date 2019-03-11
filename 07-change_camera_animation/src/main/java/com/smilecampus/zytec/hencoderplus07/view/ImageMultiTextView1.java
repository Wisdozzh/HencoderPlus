package com.smilecampus.zytec.hencoderplus07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.R;
import com.smilecampus.zytec.hencoderplus07.Utils;

public class ImageMultiTextView1 extends View {
    public static final float IMAGE_WIDTH = Utils.dp2px(100);
    public static final float IMAGE_OFFSET = Utils.dp2px(80);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor.";
    Bitmap bitmap;
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    float[] curWidth = new float[1];

    public ImageMultiTextView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    {
        bitmap = getAvatar((int) IMAGE_WIDTH);
        paint.setTextSize(Utils.dp2px(12));
        paint.getFontMetrics(fontMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, IMAGE_OFFSET, paint);

        int length = text.length();
        //记录一下高度偏移量
        float verticalOffset = - fontMetrics.top;

        for (int start = 0; start < length;) {
            float textTop = verticalOffset + fontMetrics.top;
            float textBottoom = verticalOffset + fontMetrics.bottom;

            int count;
            //需要判断图片和文字是否再一样
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_WIDTH + IMAGE_OFFSET
                    || textBottoom > IMAGE_OFFSET && textBottoom < IMAGE_WIDTH + IMAGE_OFFSET) {
                //同一行
                //判断什么时候折行
                count = paint.breakText(text, start, length, true, getWidth() - IMAGE_WIDTH, curWidth);
                canvas.drawText(text, start, count + start, IMAGE_WIDTH, verticalOffset, paint);
            } else {
                //非同一行
                count = paint.breakText(text, start, length, true, getWidth(), curWidth);
                canvas.drawText(text, start, count + start, 0, verticalOffset, paint);
            }
            start += count;
            verticalOffset += paint.getFontSpacing();
        }

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
