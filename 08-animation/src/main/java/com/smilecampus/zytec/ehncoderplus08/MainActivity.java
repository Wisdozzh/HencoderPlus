package com.smilecampus.zytec.ehncoderplus08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.smilecampus.zytec.ehncoderplus08.ui.CircleViewActivity;
import com.smilecampus.zytec.ehncoderplus08.ui.FancyFlipViewActivity;
import com.smilecampus.zytec.ehncoderplus08.ui.PointViewActivity;
import com.smilecampus.zytec.ehncoderplus08.ui.ProvinceViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCircleView(View view) {
        CircleViewActivity.start(this);
    }

    public void onPointView(View view) {
        PointViewActivity.start(this);
    }

    public void onProvinceView(View view) {
        ProvinceViewActivity.start(this);
    }

    public void onFancyFlipView(View view) {
        FancyFlipViewActivity.start(this);
    }
}
