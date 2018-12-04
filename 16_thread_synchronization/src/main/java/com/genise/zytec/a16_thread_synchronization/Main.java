package com.genise.zytec.a16_thread_synchronization;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Description
 * 是否是共享资源 共享数据
 * 进程 不可以共享数据
 * 线程 可以共享数据 才会导致线程安全问题
 * CPU线程 硬件级别 可以同时做几件事情 CPU是单核的 同时执行1件事情 CPU是四核的 那就同时可以执行4件事情
 * 操作系统线程 Thread 类似于时间分配 例如，同一时间 它把你切成100分 例如，我1ms 我把它分成100份 加入每个任务执行的时间只有1ms 那也就是用户感觉我一点儿也不卡
 * 如果卡的话 没关系 用户以为这个电脑又点儿卡 这个手机又点儿慢
 * 操作系统中支持 多进程 也支持多线程，多进程之间互相不干扰，多线程之间也互相不干扰
 * 4核8线程 就是虚拟技术 通过一些虚拟手段来让你同时执行8个线程
 * Author Genise
 * Date 2018/11/27 22:26
 *
 * 乐观锁 。 数据的修改 数据取出来再写回去 有可能两个人同时 。 先不加锁 如果看数据根原来数据不一样 再加锁
 * 悲观锁  那数据的时候 加锁 别人拿不到
 *
 * 静态方法 加锁 直接给类 加锁 xxx.class
 *
 * volatile 很轻的使某个属性具备原子性和同步性 只对基本类型具有原子性
 * double、long、有可能不是不是原子操作
 * a++ 肯定不是原子性
 *
 * AtomicXXX 一些整型 对++ -- 保证原子性
 * getAndIncrement() 就是++
 *
 * lock 和synchronized 没有区别 而且还容易报错 容易死锁 很危险
 *
 * private Lock lock = new ReentrantLock();
 * private void reset() {
 *     lock.lock();
 *     y = 0;
 *     x = 0;
 *     lock.unlock();
 * }
 * 优化
 * private void reset() {
 *    lock.lock();
 *    try {
 *       y = 0;
 *       throw new Exception();
 *       x = 0;
 *    } finally {
 *        lock.unlock();
 *    }
 *
 *  有什么用呢？比sychronize更精细
 *  读写锁
 *  private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
 *  private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
 *  private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
 *
 * private void count(int newValue) {
 *  writeLock.lock();
 *      try {
 *          x = newValue;
 *          y = newValue;
 *      } finally {
 *          writeLock.unlock();
 *      }
 *  }
 *private void print() {
 *         readLock.lock();
 *         try {
 *             System.out.println("values: " + x + ", " + y);
 *         } finally {
 *             readLock.unlock();
 *         }
 *     }
 *     我写的时候 别人既不能读 也不能写
 *     可是我读的时候 别人可以跟我一起读
 */
public class Main {

    public static void main(String[] args) {
        //带runnable和不带runnable 选择谁啊？选择谁都一样，性能完全没有区别的，只是加入你的一段可执行代码需要重用，那就写在runnable里面
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
        //原子操作 CPU级别的操作 不可能被别的线程切开来做
        //x++ 并不是原子操作 是int temp = x + 1; x = temp;
//        runSynchronized1Demo();
//        runSynchronized2Demo();

    }

    /**
     * 使用Thread类来定义工作
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                //super.run() 如果有runnable 就运行runnable
                super.run();
                System.out.println("Thread start!");
            }
        };
        //start0 是native的方法 java字节码不做这个事儿，交给虚拟机干的 负责给操作系统对接的 有些操作交给操作系统去做
        //给才做系统去做 操作系统拿出来一个额外的后台线程 然后把操作系统交给你 然后你再去执行run
        //native的start0 直接调用start方法
        //Java本身 没有切线程的能力的 必须让虚拟机去指挥操作系统的
        thread.start();
    }

    /**
     * 使用 Runnable类来定义工作
     */
    static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        //runnable在哪儿执行的 在Thread的super.run()里面执行的
        Thread thread = new Thread(runnable);
        thread.start();
    }

    //统一规则的初始化
    static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "Thread-" + count.incrementAndGet());
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "started!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        //负责指定线程来执行制定任务的接口 线程排队
        //创建 带缓存的线程池 返回ExecutorService ExecutorService是Executor的子类
        //返回ExecutorService一共有两个方法
        //shutdown 回收线程 结束executor 保守性的结束 如果当前没有线程执行 那我就结束 再来新的线程 我就不执行了  如果当前有线程执行或者有线程排队 那我就等线程都执行完再结束 但这个时候不允许有新的任务排队
        //shutdownNow 现在结束 如果执行 立即结束
        //ThreadPoolExecutor 线程池
        //newCachedThreadPool 不用自己创建线程 它帮我们创建 当我们不用的时候 他会缓存一会儿 如果再不用 那就回收
        //newSingleThreadExecutor 单线程 例如，取消 就结束了
        //newFixedThreadPool 固定线程池 可以自动回收  例如，如果我上传软件 会对图片进行批量的处理
        //newScheduledThreadPool 排任务
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        //线程池的大小和cpu的关系 可以自定义线程池一
        //第一个参数 是你刚刚代码创建的时候有几个线程 还有就是线程回收到多少个就停止了
        //第二个参数 创建到多少个线程就不再创建了
        //第三个参数、第四个参数 保持你线程不被回收的时间 60S
        //第五个参数 new SynchronousQueue() 用来装任务的
//        new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue());

//        //一次性的批量操作
//        Runnable processImagesRunnable = new Runnable() {
//            @Override
//            public void run() {
//                ...
//            }
//        };
//        ExecutorService fixedExecutor = Executors.newFixedThreadPool(20);
//        for (Bitmap bitmap:bitmaps) {
//            fixedExecutor.execute(processImagesRunnable);
//        }
//        fixedExecutor.shutdown();
    }

    //很少用 有返回值的后台 切线程的
    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "Done";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
//        //Future Java提供了一个future对象  能拿到callable返回的string 在主线程中取数据
//        //因为future的get 是一个阻塞的方法
//        //java api只能提供到这样的了 阻塞的
        Future<String> future = executor.submit(callable);
//        try {
//            String result = future.get();
//            System.out.println("result:" + result);
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }

        //一般用while true 不断的取看 future.isDone()
//        while (true) {
//            if (future.isDone()) {
//                try {
//                    String result = future.get();
//                    System.out.println("result:" + result);
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

    static void runSynchronized1Demo() {
        new Synchronized1Demo().runText();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runText();
    }
}
