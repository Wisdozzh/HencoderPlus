package com.genise.zytec.a16_thread_synchronization;

public class Synchronized3Demo implements TestDemo {
    private int x = 0;
    private int y = 0;

    private void count(int newValue) {

    }

    @Override
    public void runText() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
            }
        }.start();
    }
}
