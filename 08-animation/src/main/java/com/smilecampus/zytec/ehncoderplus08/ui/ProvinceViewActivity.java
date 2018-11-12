package com.smilecampus.zytec.ehncoderplus08.ui;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smilecampus.zytec.ehncoderplus08.R;
import com.smilecampus.zytec.ehncoderplus08.utils.ProvinceUtils;
import com.smilecampus.zytec.ehncoderplus08.view.ProvinceView;

public class ProvinceViewActivity extends AppCompatActivity {

    ProvinceView provinceView;

    public static void start(Context context) {
        Intent starter = new Intent(context, ProvinceViewActivity.class);
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
            //北京市 上海市 fraction 0.5
            int startIndex = ProvinceUtils.provinces.indexOf(startValue);
            int endIndex = ProvinceUtils.provinces.indexOf(endValue);
            int index = (int) (startIndex + (endIndex - startIndex) * fraction);
            return ProvinceUtils.provinces.get(index);
        }
    }
}
