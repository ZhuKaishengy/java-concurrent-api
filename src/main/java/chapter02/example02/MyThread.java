package chapter02.example02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 10:34
 * @Description:
 */
@Slf4j
public class MyThread extends Thread {

    private CountDownLatch countDownLatch;

    public MyThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        countDownLatch.countDown();
        log.info("Thread {} run...", Thread.currentThread().getName());
    }
}
