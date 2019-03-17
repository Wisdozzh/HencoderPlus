package com.smilecampus.zytec.a09_bitmap_drawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class  MainActivity extends AppCompatActivity {

    MaterialEditText1 editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
//        editText.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                editText.setUseFloatingLabel(false);
//            }
//        }, 3000);
    }
}
