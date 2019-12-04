package chapter02.example07;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 19:46
 * @Description: 初步使用
 */
@Slf4j
public class BatchTest {

    public static void main(String[] args) throws InterruptedException {

        // 分批操作
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> log.info("run..."));

        Thread[] threads = new Thread[6];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    log.info("{} :start...", Thread.currentThread().getName());
                    cyclicBarrier.await();
                    log.info("{} :end...", Thread.currentThread().getName());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
            // 等待 CyclicBarrier线程执行
            Thread.sleep(2000);
        }
        log.info("main thread end ...");
    }
}
