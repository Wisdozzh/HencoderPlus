package com.smilecampus.zytec.hencoderplus07;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smilecampus.zytec.hencoderplus07.ui.CameraViewActivity;
import com.smilecampus.zytec.hencoderplus07.ui.ImageMultiTextViewActivity;
import com.smilecampus.zytec.hencoderplus07.ui.SportsViewActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSportsView(View view) {
        SportsViewActivity.start(this);
    }

    public void onImageMultiTextView(View view) {
        ImageMultiTextViewActivity.start(this);
    }

    public void onCamreaView(View view) {
        CameraViewActivity.start(this);
    }
}
