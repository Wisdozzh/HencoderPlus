package com.smilecampus.zytec.hencoderplus07.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smilecampus.zytec.hencoderplus07.R;

public class CameraViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CameraViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
    }
}
