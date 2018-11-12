package com.smilecampus.zytec.ehncoderplus08.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smilecampus.zytec.ehncoderplus08.R;
import com.smilecampus.zytec.ehncoderplus08.utils.Utils;
import com.smilecampus.zytec.ehncoderplus08.view.CircleView;

public class CircleViewActivity extends AppCompatActivity {
    CircleView view;

    public static void start(Context context) {
        Intent starter = new Intent(context, CircleViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        view = findViewById(R.id.circleView);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(150));
        animator.setStartDelay(150);
        animator.setDuration(2000);
        animator.start();
    }
}
