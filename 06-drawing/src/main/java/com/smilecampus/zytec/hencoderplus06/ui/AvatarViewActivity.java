package com.smilecampus.zytec.hencoderplus06.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smilecampus.zytec.hencoderplus06.R;

public class AvatarViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, AvatarViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_view);
    }
}
