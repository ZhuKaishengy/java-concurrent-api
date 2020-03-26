package chapter04.example03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:07
 * @Description: 定制线程工厂
 */
@Slf4j
public class CachedThreadPoolTest {

    public static void main(String[] args) {

        // 用于线程池关闭
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ExecutorService executorService = Executors.newCachedThreadPool(r -> {
            // 自定义线程工厂产生线程的方法
            Thread thread = new Thread(r);
            thread.setName("haha" + (int)(Math.random() * 1000));
            return thread;
        });

        executorService.execute(() -> {
            log.info("thread:{}, run...", Thread.currentThread().getName());
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}
