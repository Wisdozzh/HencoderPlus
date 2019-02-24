package com.genise.zytec.a25_mvc_mvp_mvvm.mvp;

import com.genise.zytec.a25_mvc_mvp_mvvm.data.DataCenter;

public class Presenter {

    IView iView;

    Presenter (IView mvpActivity) {
        iView = mvpActivity;
    }

    void load() {
        String[] data = DataCenter.getData();
    }

    interface IView {
        void showData(String data1, String data2);
    }
}
