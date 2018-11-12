package com.smilecampus.zytec.hencoderplus07.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.R;
import com.smilecampus.zytec.hencoderplus07.Utils;

public class ImageMultiTextView extends View {
    public static final float IMAGE_WIDTH = Utils.dp2px(100);
    public static final float IMAGE_OFFSET = Utils.dp2px(80);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean justo sem, sollicitudin in maximus a, vulputate id magna. Nulla non quam a massa sollicitudin commodo fermentum et est. Suspendisse potenti. Praesent dolor dui, dignissim quis tellus tincidunt, porttitor vulputate nisl. Aenean tempus lobortis finibus. Quisque nec nisl laoreet, placerat metus sit amet, consectetur est. Donec nec quam tortor. Aenean aliquet dui in enim venenatis, sed luctus ipsum maximus. Nam feugiat nisi rhoncus lacus facilisis pellentesque nec vitae lorem. Donec et risus eu ligula dapibus lobortis vel vulputate turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In porttitor, risus aliquam rutrum finibus, ex mi ultricies arcu, quis ornare lectus tortor nec metus. Donec ultricies metus at magna cursus congue. Nam eu sem eget enim pretium venenatis. Duis nibh ligula, lacinia ac nisi vestibulum, vulputate lacinia tortor.";
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    float[] curWidth = new float[1];

    public ImageMultiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getBitmap((int) Utils.dp2px(100));
        paint.setTextSize(Utils.dp2px(12));
        paint.getFontMetrics(fontMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //图片居右
//        canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, IMAGE_OFFSET, paint);
        //图片居中
//        canvas.drawBitmap(bitmap, (getWidth() - IMAGE_WIDTH)/2, IMAGE_OFFSET, paint);
        //图片居左
        canvas.drawBitmap(bitmap, 0, IMAGE_OFFSET, paint);


        int length = text.length();
        float verticalOffset = - fontMetrics.top;

/**
        //measuredWidth If not null, returns the actual width measured.
        int index = paint.breakText(text, true, getWidth(), curWidth);
//        canvas.drawText(text,0,(fontMetrics.descent - fontMetrics.ascent),paint);
        canvas.drawText(text, 0, index, 0, verticalOffset, paint);
//第二行 高度
        int secondIndex = paint.breakText(text, index, text.length(), true, getWidth(), curWidth);
        canvas.drawText(text,index,index + secondIndex,0,verticalOffset + paint.getFontSpacing(),paint);
//第三行
        int thirdIndex = paint.breakText(text, index + secondIndex, text.length(), true, getWidth(), curWidth);
        canvas.drawText(text,index + secondIndex,index + secondIndex + thirdIndex,
                0,verticalOffset + paint.getFontSpacing() * 2,paint);
 */

/**      图片居右
         for (int start = 0; start < length;) {
            int maxWidth;
            float textTop = verticalOffset + fontMetrics.top;
            float textBottom = verticalOffset + fontMetrics.bottom;
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH
                    ||textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
                //代表文本和图片一行
                maxWidth = (int) (getWidth() - IMAGE_WIDTH);
            } else {
                //代表图片和文本非一行
                maxWidth = getWidth();
            }
            int count = paint.breakText(text, start, length, true, maxWidth, curWidth);
            canvas.drawText(text, start, start + count, 0, verticalOffset, paint);
            start += count;
            verticalOffset += paint.getFontSpacing();
        }
 */

 /**        图片居中
        for (int start = 0; start < length;) {
            int maxWidth;
            float textTop = verticalOffset + fontMetrics.top;
            float textBottom = verticalOffset + fontMetrics.bottom;
            int count;
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH
                    ||textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
                //代表文本和图片一行
                maxWidth = (int) (getWidth() - IMAGE_WIDTH);
                count = paint.breakText(text, start, length, true, maxWidth / 2, curWidth);
                canvas.drawText(text, start, start + count, 0, verticalOffset, paint);
                count = paint.breakText(text, start, length, true, maxWidth / 2, curWidth);
                canvas.drawText(text, start, start + count, maxWidth/2 + IMAGE_WIDTH, verticalOffset, paint);
            } else {
                //代表图片和文本非一行
                maxWidth = getWidth();
                count = paint.breakText(text, start, length, true, maxWidth, curWidth);
                canvas.drawText(text, start, start + count, 0, verticalOffset, paint);
            }
            start += count;
            verticalOffset += paint.getFontSpacing();
        }
  */

        for (int start = 0; start < length;) {
            int maxWidth;
            float textTop = verticalOffset + fontMetrics.top;
            float textBottom = verticalOffset + fontMetrics.bottom;
            int count;
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH
                    ||textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
                //代表文本和图片一行
                maxWidth = (int) (getWidth() - IMAGE_WIDTH);
                count = paint.breakText(text, start, length, true, maxWidth, curWidth);
                canvas.drawText(text, start, start + count, IMAGE_WIDTH, verticalOffset, paint);
            } else {
                //代表图片和文本非一行
                maxWidth = getWidth();
                count = paint.breakText(text, start, length, true, maxWidth, curWidth);
                canvas.drawText(text, start, start + count, 0, verticalOffset, paint);
            }
            start += count;
            verticalOffset += paint.getFontSpacing();
        }
    }

    Bitmap getBitmap(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }
}
