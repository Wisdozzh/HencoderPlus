package com.smilecampus.zytec.ehncoderplus08.ui;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smilecampus.zytec.ehncoderplus08.R;
import com.smilecampus.zytec.ehncoderplus08.utils.ProvinceUtils;
import com.smilecampus.zytec.ehncoderplus08.view.ProvinceView;
import com.smilecampus.zytec.ehncoderplus08.view.ProvinceView1;

public class ProvinceViewActivity1 extends AppCompatActivity {

    ProvinceView1 provinceView;

    public static void start(Context context) {
        Intent starter = new Intent(context, ProvinceViewActivity1.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_view);
        provinceView = findViewById(R.id.provinceView);

        ObjectAnimator animator = ObjectAnimator.ofObject(provinceView, "province", new ProvinceEvaluator(),"澳门特别行政区");
        animator.setStartDelay(150);
        animator.setDuration(3000);
        animator.start();
    }

    class ProvinceEvaluator implements TypeEvaluator<String> {

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int startIndex = ProvinceUtils.provinces.indexOf(startValue);
            int endIndex = ProvinceUtils.provinces.indexOf(endValue);
            int index = (int) (startIndex + (endIndex - startIndex) * fraction);
            return ProvinceUtils.provinces.get(index);
        }
    }
}
