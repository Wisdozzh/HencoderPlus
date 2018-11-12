package com.smilecampus.zytec.a10_layout.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            /**
//             * childWidthSpec  要求：开发者对子View的要求 + 可用控件（复杂）
//             child.measure(childWidthSpec, childHeightSpec);
//
//             Rect childBound = childrenBounds.get(i);
//             childBound.set(childBound);
//             */
//            /***************这是思路*****************/
//            //开发者要求
//            LayoutParams layoutParams = child.getLayoutParams();
//            //父View对我的要求 看可用空间的
//            int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
//            int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//            int childWidthMode;
//            int childWidthSize;
//            switch (layoutParams.width) {
//                case LayoutParams.MATCH_PARENT:
//                    switch (specWidthMode) {
//                        //不知道自己有多大 父View给我一个上限 和精确一样操作
//                        case MeasureSpec.AT_MOST:
//                            //父View要求我必须量出具体值 精确尺寸
//                            //
//                        case MeasureSpec.EXACTLY:
//                            childWidthMode = MeasureSpec.EXACTLY;
//                            childWidthSize = specWidthSize - userWidth;
//                            MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode);
//                            break;
//                        //没上限
//                        case MeasureSpec.UNSPECIFIED:
//                            childWidthMode = MeasureSpec.UNSPECIFIED;
//                            childWidthSize = 0;//直接写0 就ok了
//
//                    }
//                    break;
//                case LayoutParams.WRAP_CONTENT:
//                    break;
//
//            }
//        }

        /**
         int width = ?;
         int height = ?;
         setMeasuredDimension(width,height);
         */

        int widthUsed = 0;
        int heightUsed = 0;
        int lineWidithUsed = 0;
        int lineMaxHeight = 0;
        int sepcWidth = MeasureSpec.getSize(widthMeasureSpec);//父控件的宽度
        int sepcMode = MeasureSpec.getMode(widthMeasureSpec);//父控件的宽度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //一个方法搞定了上面的思路
//            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
            //解决压缩问题 由于压缩 textView没有进行二次测量 所以将widthUsed设置为0 让它认为一直这么量下去
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            //手动判断折行
            if (sepcMode != MeasureSpec.UNSPECIFIED &&
                    lineWidithUsed + child.getMeasuredWidth() > sepcWidth) {
                lineWidithUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();

                childrenBounds.add(childBound);

            } else {
                childBound = childrenBounds.get(i);
            }
            //hild.getMeasuredWidth() 子View量出自己的宽度
            childBound.set(lineWidithUsed, heightUsed, lineWidithUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            lineWidithUsed += child.getMeasuredWidth();
            widthUsed = Math.max(widthUsed, lineWidithUsed);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());

            //折行
        }

        //margin padding
        int width = widthUsed;
        int height = heightUsed +
                lineMaxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = childrenBounds.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    //由于margin需要特殊的layoutparams才能得到的 需要声明才行MarginLayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
