package com.smilecampus.zytec.ehncoderplus08.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smilecampus.zytec.ehncoderplus08.R;
import com.smilecampus.zytec.ehncoderplus08.utils.Utils;
import com.smilecampus.zytec.ehncoderplus08.view.CircleView;
import com.smilecampus.zytec.ehncoderplus08.view.CircleView1;

public class CircleViewActivity1 extends AppCompatActivity {
    CircleView1 view;

    public static void start(Context context) {
        Intent starter = new Intent(context, CircleViewActivity1.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        view = findViewById(R.id.circleView);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(150));
        animator.setStartDelay(1000);
        animator.setDuration(1500);
        animator.start();
    }
}
