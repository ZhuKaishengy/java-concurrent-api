package chapter08.example01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 17:32
 * @Description:
 */
@Slf4j
public class SimpleTest {

    /**
     * ScheduledThreadPoolExecutor使用Callable延迟运行
     * 单线程线程池，任务到达执行时间，只要有空余线程就会执行
     */
    @Test
    public void test01() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        log.info("main thread start...");
        ScheduledFuture<String> future1 = scheduledExecutorService.schedule(() -> {
            String name = Thread.currentThread().getName();
            log.info("task1 thread:{} begin", name);
            Thread.sleep(3000);
            log.info("task1 thread:{} end", name);
            return "success";
        }, 5, TimeUnit.SECONDS);

        ScheduledFuture<String> future2 = scheduledExecutorService.schedule(() -> {
            String name = Thread.currentThread().getName();
            log.info("task2 thread:{} begin", name);
            Thread.sleep(3000);
            log.info("task2 thread:{} end", name);
            return "success";
        }, 5, TimeUnit.SECONDS);

        String result1 = future1.get();
        String result2 = future2.get();
        log.info("main thread result1:{}, result2:{}", result1, result2);
    }

    /**
     * 多线程线程池，任务到达执行时间，只要有空余线程就会并发执行
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test02() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        log.info("main thread start...");
        ScheduledFuture<String> future1 = scheduledExecutorService.schedule(() -> {
            String name = Thread.currentThread().getName();
            log.info("task1 thread:{} begin", name);
            Thread.sleep(3000);
            log.info("task1 thread:{} end", name);
            return "success";
        }, 5, TimeUnit.SECONDS);

        ScheduledFuture<String> future2 = scheduledExecutorService.schedule(() -> {
            String name = Thread.currentThread().getName();
            log.info("task2 thread:{} begin", name);
            Thread.sleep(3000);
            log.info("task2 thread:{} end", name);
            return "success";
        }, 5, TimeUnit.SECONDS);

        String result1 = future1.get();
        String result2 = future2.get();
        log.info("main thread result1:{}, result2:{}", result1, result2);
    }

    /**
     * ScheduledThreadPoolExecutor使用Runnable延迟运行
     * @throws InterruptedException
     */
    @Test
    public void test03() throws InterruptedException {
        // 保证执行顺序
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        log.info("main thread start...");
        scheduledExecutorService.schedule(() -> {
            String name = Thread.currentThread().getName();
            log.info("task thread:{} begin", name);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("task thread:{} end", name);
            countDownLatch.countDown();
        }, 5, TimeUnit.SECONDS);

        countDownLatch.await();
        log.info("main thread end...");
    }
}
