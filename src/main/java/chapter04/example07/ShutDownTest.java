package chapter04.example07;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/25 13:55
 * @Description: 方法shutdown()和shutdownNow()与返回值
 */
@Slf4j
public class ShutDownTest {

    /**
     * 调用shutdown，线程池中正在执行的线程和队列中等待的线程都会继续执行完毕
     * @throws InterruptedException
     */
    @Test
    public void testShutDown01() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Phaser phaser = new Phaser(2);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 1。先启动第一个线程
        executor.execute(() -> {
            log.info("开始:{}", Thread.currentThread().getName());
            // 通知第二个线程启动
            countDownLatch.countDown();
            // 等待主线程执行完，关闭 ThreadPoolExecutor
            phaser.arriveAndAwaitAdvance();
            log.info("结束:{}", Thread.currentThread().getName());
        });
        countDownLatch.await();
        // 2。第一个线程开始执行，第二个线程启动，此时，他在队列中
        executor.execute(() -> {
            log.info("2开始:{}", Thread.currentThread().getName());
            log.info("2结束:{}", Thread.currentThread().getName());
        });
        // 在调用shutdown的时候，正在执行的线程会继续执行，在队列中的鲜橙也会继续执行
        executor.shutdown();
        log.info("shutdown...");
        phaser.arrive();
    }

    /**
     * 不再添加新的任务Task
     */
    @Test
    public void testShutDown02() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Phaser phaser = new Phaser(2);
        // 1。先启动第一个线程
        executor.execute(() -> {
            log.info("开始:{}", Thread.currentThread().getName());
            // 等待主线程执行完，关闭 ThreadPoolExecutor
            phaser.arriveAndAwaitAdvance();
            log.info("结束:{}", Thread.currentThread().getName());
        });
        // 2。在调用shutdown的时候，正在执行的线程会继续执行，在队列中的鲜橙也会继续执行
        executor.shutdown();
        log.info("shutdown...");
        /*
         * 3。shutdown之后在尝试添加新任务
         * java.util.concurrent.RejectedExecutionException
         */
        executor.execute(() -> {
            log.info("2开始:{}", Thread.currentThread().getName());
            log.info("2结束:{}", Thread.currentThread().getName());
        });
        phaser.arrive();
    }

    /**
     * 在调用shutdownnow的时候，正在执行的线程会执行完毕，队列中的线程会不再执行
     * @throws InterruptedException
     */
    @Test
    public void testShutDownNow01() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Phaser phaser = new Phaser(2);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 1。先启动第一个线程
        executor.execute(() -> {
            log.info("开始:{}", Thread.currentThread().getName());
            // 通知第二个线程启动
            countDownLatch.countDown();
            // 等待主线程执行完，关闭 ThreadPoolExecutor
            phaser.arriveAndAwaitAdvance();
            log.info("结束:{}", Thread.currentThread().getName());
        });
        countDownLatch.await();
        // 2。第一个线程开始执行，第二个线程启动，此时，他在队列中
        executor.execute(() -> {
            log.info("2开始:{}", Thread.currentThread().getName());
            log.info("2结束:{}", Thread.currentThread().getName());
        });
        // 3. 在调用shutdownnow的时候，正在执行的线程会执行完毕，队列中的线程会不再执行，返回中断的线程
        List<Runnable> runnables = executor.shutdownNow();
        log.info("shutdownnow...，被中断的线程数量:{}", runnables.size());
        phaser.arrive();
    }
    /**
     * 不再添加新的任务Task
     */
    @Test
    public void testShutDownNow02() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Phaser phaser = new Phaser(2);
        // 1。先启动第一个线程
        executor.execute(() -> {
            log.info("开始:{}", Thread.currentThread().getName());
            // 等待主线程执行完，关闭 ThreadPoolExecutor
            phaser.arriveAndAwaitAdvance();
            log.info("结束:{}", Thread.currentThread().getName());
        });
        // 2。在调用shutdown的时候，正在执行的线程会继续执行，在队列中的鲜橙也会继续执行
        executor.shutdownNow();
        log.info("shutdownnow...");
        /*
         * 3。shutdown之后在尝试添加新任务
         * java.util.concurrent.RejectedExecutionException
         */
        executor.execute(() -> {
            log.info("2开始:{}", Thread.currentThread().getName());
            log.info("2结束:{}", Thread.currentThread().getName());
        });
        phaser.arrive();
    }

    /**
     * 只要调用了shutdown()或shutdownNow()方法，isShutdown()方法的返回值就是true。
     */
    @Test
    public void testIsShutDown() {
        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        log.info("isShutDown:{}", executor1.isShutdown());
        executor1.shutdown();
        log.info("isShutDown:{}", executor1.isShutdown());
        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        log.info("isShutDown:{}", executor2.isShutdown());
        executor2.shutdownNow();
        log.info("isShutDown:{}", executor2.isShutdown());

    }
}
