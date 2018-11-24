package com.genise.zytec.a15_drag_nestedscroll.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DragListenerGridView extends ViewGroup {
    public static final int COLUMNS = 2;
    public static final int ROWS = 3;

    ViewConfiguration configuration;
    OnDragListener dragListener = new HenDragListener();
    View dragView;
    List<View> orderedChildren = new ArrayList<>();

    public DragListenerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configuration = ViewConfiguration.get(context);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            orderedChildren.add(child);
            child.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dragView = v;
                    v.startDrag(null, new DragShadowBuilder(v), v, 0);
                    return false;
                }
            });
            //6个view 都去相应draglistener
            //如果每个都去实现 会不会很消耗性能？ 不会的 因为是空实现
            child.setOnDragListener(dragListener);
        }
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
            view.layout(0, 0, childWidth, childHeight);
            view.setTranslationX(childLeft);
            view.setTranslationY(childTop);
        }
    }

    private class HenDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                //一开始 有人被拖起来了
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getLocalState() == v) {
                        v.setVisibility(INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (event.getLocalState() != v) {
                        //重新排序
                        sort(v);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setVisibility(VISIBLE);
                    break;
            }
            return true;
        }

        private void sort(View targetView) {
            int dragIndex = -1;
            int targetIndex = -1;
            for (int i = 0; i < getChildCount(); i++) {
                View child = orderedChildren.get(i);
                if (targetView == child) {
                    targetIndex = i;
                } else if (dragView == child){
                    dragIndex = i;
                }
            }
            if (targetIndex < dragIndex) {
                orderedChildren.remove(dragIndex);
                orderedChildren.add(targetIndex, dragView);
            } else if (targetIndex > dragIndex) {
                orderedChildren.remove(dragIndex);
                orderedChildren.add(targetIndex, dragView);
            }
            int childLeft;
            int childTop;
            int childWidth = getWidth() / COLUMNS;
            int childHeight = getHeight() / ROWS;
            for (int index = 0; index < getChildCount(); index++) {
                View child = orderedChildren.get(index);
                childLeft = index % 2 * childWidth;
                childTop = index / 2 * childHeight;
                child.animate()
                        .translationX(childLeft)
                        .translationY(childTop)
                        .setDuration(150);
            }
        }
    }
}
