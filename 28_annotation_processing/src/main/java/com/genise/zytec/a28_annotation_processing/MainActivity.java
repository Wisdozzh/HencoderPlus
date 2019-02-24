package com.genise.zytec.a28_annotation_processing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.genise.zytec.a28_lib_annotation_processor.Binding;
import com.genise.zytec.a28_lib_annotations.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Binding.bind(this);

//        textView.setText("哈哈哈");
    }
}
