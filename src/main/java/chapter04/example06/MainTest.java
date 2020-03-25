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
                new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(30));

        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        log.info("corePoolSize:{};maximumPoolSize:{}", corePoolSize,maximumPoolSize);
    }

    /**
     * 在线程池中添加的线程数量<=corePoolSize
     */
    @Test
    public void test2() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(30));

        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                Awaitility.await()
                        .atMost(2, TimeUnit.SECONDS).until(() -> log.info("threadName:{} end", Thread.currentThread().getName()));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
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
                Awaitility.await()
                        .atMost(2, TimeUnit.SECONDS).until(() -> log.info("threadName:{} end", Thread.currentThread().getName()));
                countDownLatch.countDown();
            });
            log.info("remain cap:{}", threadPoolExecutor.getQueue().remainingCapacity());
        }
        countDownLatch.await();
    }

    /**
     * 数量>corePoolSize并且<=maximumPoolSize的情况
     * SynchronousQueue
     */
    @Test
    public void test3_2() {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS, new SynchronousQueue<>(true));

        for (int i = 0; i < 4; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                Awaitility.await()
                        .atMost(2, TimeUnit.SECONDS).until(() -> log.info("threadName:{} end", Thread.currentThread().getName()));
            });
            log.info("remain cap:{}", threadPoolExecutor.getQueue().remainingCapacity());
        }
        ExecutorKit.shutdownAndWait(threadPoolExecutor);
    }

    /**
     * 数量>maximumPoolSize的情况
     * LinkedBlockingDeque
     */
    @Test
    public void test4_1() {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(30));

        for (int i = 0; i < 8; i++) {
            threadPoolExecutor.execute(() -> {
                log.info("threadName:{} begin", Thread.currentThread().getName());
                // keep alive timeout
                Awaitility.await()
                        .atMost(2, TimeUnit.SECONDS).until(() -> log.info("threadName:{} end", Thread.currentThread().getName()));
            });
            log.info("remain cap:{}", threadPoolExecutor.getQueue().remainingCapacity());
        }
        ExecutorKit.shutdownAndWait(threadPoolExecutor);
    }

    /**
     * 数量>maximumPoolSize的情况
     * SynchronousQueue
     */
    @Test
    public void test4_2() {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3, 10, 1, TimeUnit.SECONDS,
                        new SynchronousQueue<>());

        // 线程同步
        CyclicBarrier barrier = new CyclicBarrier(8,() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("pool size:{}", threadPoolExecutor.getPoolSize());
        });

        for (int i = 0; i < 8; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    // 等待所有线程启动
                    barrier.await();
                    log.info("threadName:{} begin", Thread.currentThread().getName());
                    Thread.sleep(2000);
                    // 等待所有线程执行完
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        while (barrier.getParties() == 8) {

        }
    }

}
