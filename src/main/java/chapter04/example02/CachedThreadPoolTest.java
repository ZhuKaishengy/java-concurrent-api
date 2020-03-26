package chapter04.example02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:07
 * @Description:
 */
@Slf4j
public class CachedThreadPoolTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                log.info("[first] thread:{}, execute...", Thread.currentThread().getName());
                latch.countDown();
            });
        }
        try {
            // 保证上面的线程执行完了，验证下面的循环是否会复用上面的线程
            latch.await();
            for (int i = 0; i < 3; i++) {
                executorService.execute(() -> log.info("[second] thread:{}, execute...", Thread.currentThread().getName()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
