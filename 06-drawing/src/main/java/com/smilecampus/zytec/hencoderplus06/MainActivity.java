package com.smilecampus.zytec.hencoderplus06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.smilecampus.zytec.hencoderplus06.ui.AvatarViewActivity;
import com.smilecampus.zytec.hencoderplus06.ui.DashBoardActivity;
import com.smilecampus.zytec.hencoderplus06.ui.PieChartActivity;
import com.smilecampus.zytec.hencoderplus06.view.AvatarView;
import com.smilecampus.zytec.hencoderplus06.view.PieChart;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDashBoard(View view) {
        DashBoardActivity.start(this);
    }

    public void onPieChart(View view) {
        PieChartActivity.start(this);
    }

    public void onAvatarView(View view) {
        AvatarViewActivity.start(this);
    }
}
