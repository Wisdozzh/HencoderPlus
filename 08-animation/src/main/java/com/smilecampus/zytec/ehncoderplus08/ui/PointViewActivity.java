package com.smilecampus.zytec.ehncoderplus08.ui;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smilecampus.zytec.ehncoderplus08.R;
import com.smilecampus.zytec.ehncoderplus08.utils.Utils;
import com.smilecampus.zytec.ehncoderplus08.view.PointView;

public class PointViewActivity extends AppCompatActivity {

    PointView pointView;

    public static void start(Context context) {
        Intent starter = new Intent(context, PointViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_view);
        pointView = findViewById(R.id.pointview);

        Point targetPoint = new Point((int)Utils.dp2px(300), (int)Utils.dp2px(300));
        ObjectAnimator animator = ObjectAnimator.ofObject(pointView, "point", new PointEvaluator(), targetPoint);
        animator.setStartDelay(150);
        animator.setDuration(2000);
        animator.start();
    }

    class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            //(1,1) (5,5) fraction 0.2
            float x = startValue.x + (endValue.x - startValue.x) * fraction;
            float y = startValue.y + (endValue.y - startValue.y) * fraction;
            return new Point((int)x,(int)y);
        }
    }
}
