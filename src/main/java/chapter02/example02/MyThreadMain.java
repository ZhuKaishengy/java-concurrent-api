package chapter02.example02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 10:36
 * @Description:
 */
@Slf4j
public class MyThreadMain {

    public static void main(String[] args) {
        MyThread[] threads = new MyThread[10];
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(countDownLatch);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            countDownLatch.await();
            log.info("count = 0");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
