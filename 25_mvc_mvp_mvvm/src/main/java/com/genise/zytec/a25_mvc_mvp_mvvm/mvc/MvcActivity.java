package com.genise.zytec.a25_mvc_mvp_mvvm.mvc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.genise.zytec.a25_mvc_mvp_mvvm.R;
import com.genise.zytec.a25_mvc_mvp_mvvm.data.DataCenter;

public class MvcActivity extends AppCompatActivity {
    IView dataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataView = findViewById(R.id.dataView);

        String[] data = DataCenter.getData();
        dataView.showData(data[0], data[1]);
    }

    interface IView {
        void showData(String data1, String data2);
    }
}
