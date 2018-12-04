package com.genise.zytec.a16_thread_synchronization;
/**
 * Description synchronized 做两件事情
 * 1、资源的互斥访问 所有的添加了synchronized 都会被monitor所监视
 * 2、对监视资源的数据同步
 * this 就是monitor
 * suchronized(this) {
 *
 * }
 * 线程安全
 * safety 是别改坏了 翻译过来的问题
 * Security 才是真正的安全
 * Author Genise
 * Date 2018/11/27 23:02
 */
public class Synchronized1Demo implements TestDemo {
    private int x = 0;
    private int y = 0;

    //synchronized 当你给一个方法加这个关键子的时候 他会给这个方法加一个monitor（监视器） 加一个阻碍
    //然后每一个想要调用这个被synchronized修改的方法或者代码块的时候  当调用这个方法的时候 看看monitor是不是在这儿看着
    //monitor 只能允许一个线程去执行这个方法的的
    //synchronized 是多个线程 去协商一下 怎么做这个事情
    private synchronized void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x: " + x + ", y:" + y);
        }
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
