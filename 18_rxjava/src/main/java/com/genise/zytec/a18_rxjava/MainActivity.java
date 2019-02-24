package com.genise.zytec.a18_rxjava;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.genise.zytec.a18_rxjava.model.Repo;
import com.genise.zytec.a18_rxjava.network.Api;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        Api api = retrofit.create(Api.class);

//        api.getRepos("rengwuxian")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<List<Repo>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                        textView.setText("正在请求。。。。");
//                    }
//
//                    @Override
//                    public void onSuccess(List<Repo> repos) {
//                        textView.setText(repos.get(0).getName());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        String errorDesc = e.getMessage();
//                        if (errorDesc == null) {
//                            errorDesc = e.getClass().getName();
//                        }
//                        textView.setText(errorDesc);
//                    }
//                });

        Single.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        api.getRepos("rengwuxian")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess()
                .observeOn(Schedulers.io())
                .doOnSuccess()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        Handler
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
