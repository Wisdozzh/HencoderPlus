package com.genise.zytec.a15_drag_nestedscroll.drag;

import android.content.ClipData;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.genise.zytec.a15_drag_nestedscroll.R;

public class DragToCollectLayout extends RelativeLayout {

    ImageView avatarView;
    ImageView logoView;
    LinearLayout collectorLayout;

    View.OnLongClickListener dragStarter = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData imageData = ClipData.newPlainText("name", v.getContentDescription());
            //可以跨进城 新版的Android 支持跨进城的拖拽了
            //将图片拖拽 直接扔到微信里面 就是因为跨进城 比较重 只能松手的时候才能拿到
            //API 26 删除卸载 drag并且扔
            //某些用户 眼睛不好用 摸到会给提示 XML ContentDescription
            //new View.DragShadowBuilder(v) 原大小的view 传递过去
            //其实拽起来的 并不是view 而是在屏幕最上面的一个像素
            //跨进程是通过onDragEvent 来传递View的吗？ 是的
            return ViewCompat.startDragAndDrop(v, imageData, new View.DragShadowBuilder(v), null, 0);
        }
    };
    OnDragListener dragListener = new CollectListener();

    public DragToCollectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        avatarView = findViewById(R.id.avatarView);
        logoView = findViewById(R.id.logoView);
        collectorLayout = findViewById(R.id.collectorLayout);

        avatarView.setOnLongClickListener(dragStarter);
        logoView.setOnLongClickListener(dragStarter);
        collectorLayout.setOnDragListener(dragListener);
    }

    class CollectListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    if (v instanceof LinearLayout) {
                        LinearLayout layout = (LinearLayout) v;
                        TextView textView = new TextView(getContext());
                        textView.setTextSize(16);
                        textView.setText(event.getClipData().getItemAt(0).getText());
                        layout.addView(textView);
                    }
                    break;
            }
            return true;
        }
    }
}
