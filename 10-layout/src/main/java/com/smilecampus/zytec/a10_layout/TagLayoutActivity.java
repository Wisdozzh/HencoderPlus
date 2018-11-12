package com.smilecampus.zytec.a10_layout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TagLayoutActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, TagLayoutActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_layout);
    }
}
