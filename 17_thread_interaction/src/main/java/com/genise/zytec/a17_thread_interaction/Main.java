package com.genise.zytec.a17_thread_interaction;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        runThreadInteractionDemo();
//        runWaitDemo();
//        runCustomizableThreadDemo();
//        List<String> provinces = Arrays.asList("北京市",
//                "天津市",
//                "上海市");

//        List<String> provinces = Arrays.asList();
//        for (String s:provinces) {
//            System.out.println(s);
//        }
//        System.out.println(provinces);

    }

    static void runThreadInteractionDemo() {
        new ThreadInteractionDemo().runTest();
    }

    static void runWaitDemo() {
        new WaitDemo().runTest();
    }

    static void runCustomizableThreadDemo() {
        new CustomizableThreadDemo().runTest();
    }
}
