package com.genise.zytec.a16_thread_synchronization;

public class Synchronized2Demo implements TestDemo {
    private int x = 0;

    private synchronized void count() {
        x++;
    }
    
    @Override
    public void runText() {
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
            }
        }.start();
    }
}
