package com.genise.zytec.a05_okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();
        client.newCall(new Request.Builder()
                .url("http://api.github.com/users/rengwuxian/repo")//默认 get请求
                .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("failed!");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("success!");
                    }
                });
    }
}
