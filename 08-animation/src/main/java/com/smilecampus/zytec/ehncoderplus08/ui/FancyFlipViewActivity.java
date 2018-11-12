package com.smilecampus.zytec.ehncoderplus08.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smilecampus.zytec.ehncoderplus08.R;
import com.smilecampus.zytec.ehncoderplus08.utils.Utils;
import com.smilecampus.zytec.ehncoderplus08.view.FancyFlipView;

public class FancyFlipViewActivity extends AppCompatActivity {

    FancyFlipView fancyFlipView;

    public static void start(Context context) {
        Intent starter = new Intent(context, FancyFlipViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_flip_view);
        fancyFlipView = findViewById(R.id.fancyFlipView);
/*
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(fancyFlipView, "bottomFlip", 45);
        animator1.setDuration(1500);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fancyFlipView, "flipRotation", 270);
        animator2.setDuration(1500);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(fancyFlipView, "topFlip", -45);
        animator3.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1, animator2, animator3);
        animatorSet.setStartDelay(1000);
        animatorSet.start();
        */


        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -45);
        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 45);
        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(fancyFlipView, bottomFlipHolder, flipRotationHolder, topFlipHolder);
        animator.setStartDelay(1000);
        animator.setDuration(2000);
        animator.start();

    }
}
