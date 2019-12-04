package chapter02.example10;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 13:32
 * @Description: 方法isBroken()的使用
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> log.info("touch barrier"));

        MyService myService = new MyService(cyclicBarrier);

        MyThread thread1 = new MyThread(myService);
        thread1.setName("thread1");
        thread1.start();
        MyThread thread2 = new MyThread(myService);
        thread2.setName("thread2");
        thread2.start();
        MyThread thread3 = new MyThread(myService);
        thread3.setName("thread3");
        thread3.start();
        MyThread thread4 = new MyThread(myService);
        thread4.setName("thread4");
        thread4.start();
    }
}
