package com.smilecampus.zytec.a10_layout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SquareImageViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SquareImageViewActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_image_view);
    }
}
