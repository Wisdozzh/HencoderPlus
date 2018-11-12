package com.smilecampus.zytec.a10_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSquareImageView(View view) {
        SquareImageViewActivity.start(this);
    }

    public void onCircleView(View view) {
        CircleViewActivity.start(this);
    }

    public void onTagLayout(View view) {
        TagLayoutActivity.start(this);
    }
}
