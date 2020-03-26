package chapter04.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:46
 * @Description: 有界线程池,核心线程数和最大线程数都为固定的值
 */
@Slf4j
public class FixThreadPool {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        // 有界线程池，核心线程数和最大线程数都为2
        ExecutorService executorService = Executors.newFixedThreadPool(2, r -> {
            Thread thread = new Thread(r);
            thread.setName("th-" + (int)(Math.random() * 1000));
            return thread;
        });

        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                log.info("thread:{} run ...", Thread.currentThread().getName());
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
