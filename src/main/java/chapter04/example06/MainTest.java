package chapter04.example06;

import common.ExecutorKit;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/11 10:09
 * @Description:
 */
@RunWith(JUnit4.class)
@Slf4j
public class MainTest {

    /**
     * 前2个参数与getCorePoolSize()和getMaximumPoolSize()方法
     */
    @Test
    public void test1() {

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(30));

        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        log.info("corePoolSize:{};maximumPoolSize:{}", corePoolSize,maximumPoolSize);
        threadPoolExecutor.shutdown();
    }

    /**
     * 在线程池中添加的线程数量<=corePoolSize，创建核心线程数个线程，不会清除
     */
    @Test
    public void test2() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(30));

        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                try {
                    Thread.sleep(2000);
                    log.info("threadName:{} end", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        int size = threadPoolExecutor.getQueue().size();
        log.info("queue 中 任务数：{}", size);
        threadPoolExecutor.shutdown();
    }

    /**
     * 数量>corePoolSize并且<=maximumPoolSize的情况
     * LinkedBlockingDeque
     */
    @Test
    public void test3_1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(30));

        for (int i = 0; i < 4; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                try {
                    int size = threadPoolExecutor.getQueue().size();
                    log.info("queue 中 任务数：{}", size);
                    Thread.sleep(2000);
                    log.info("threadName:{} end", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        threadPoolExecutor.shutdown();
    }

    /**
     * 数量>corePoolSize并且<=maximumPoolSize的情况
     * SynchronousQueue：并发数不能超过最大线程数，否则抛出异常
     */
    @Test
    public void test3_2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                        new SynchronousQueue<>(true));

        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                try {
                    int size = threadPoolExecutor.getQueue().size();
                    log.info("queue 中 任务数：{}", size);
                    Thread.sleep(2000);
                    log.info("threadName:{} end", Thread.currentThread().getName());
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        Thread.sleep(2000);
        int size = threadPoolExecutor.getPoolSize();
        // 超时后清除多余线程
        log.info("pool 中 线程：{}", size);
        threadPoolExecutor.shutdown();
    }

    /**
     * 数量>maximumPoolSize的情况
     * LinkedBlockingDeque
     */
    @Test
    public void test4_1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(30));

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                try {
                    int size = threadPoolExecutor.getQueue().size();
                    log.info("queue 中 任务数：{}", size);
                    Thread.sleep(2000);
                    log.info("threadName:{} end", Thread.currentThread().getName());
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        Thread.sleep(2000);
        int size = threadPoolExecutor.getPoolSize();
        // 超时后清除多余线程
        log.info("pool 中 线程：{}", size);
        threadPoolExecutor.shutdown();
    }

    /**
     * keepalive=0，使用后立刻清除
     * SynchronousQueue
     */
    @Test
    public void test4_2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 0, TimeUnit.SECONDS,
                        new SynchronousQueue<>(true));

        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                log.info("threadName:{} end", Thread.currentThread().getName());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        int size = threadPoolExecutor.getPoolSize();
        log.info("now pool 中 线程个数：{}", size);
        Thread.sleep(2000);
        size = threadPoolExecutor.getPoolSize();
        log.info("later pool 中 线程个数：{}", size);
        threadPoolExecutor.shutdown();
    }

}
