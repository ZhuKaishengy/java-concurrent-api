package chapter04.example13;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 13:05
 * @Description:
 */
@Slf4j
public class MinorTest {

    /**
     * 取得已经执行完成的任务数
     * @throws InterruptedException
     */
    @Test
    public void testCompletedTaskCount() throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));

        // 保证@test单元测试主线程等待
        CountDownLatch countDownLatch = new CountDownLatch(5);
        // 保证顺序执行
        ReentrantLock reentrantLock = new ReentrantLock(true);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                reentrantLock.lock();
                log.info("thread name:{},run...",Thread.currentThread().getName());
                long completedTaskCount = executor.getCompletedTaskCount();
                log.info("thread name:{}, completedTaskCount:{}", Thread.currentThread().getName(), completedTaskCount);
                countDownLatch.countDown();
                reentrantLock.unlock();
            });
        }
        countDownLatch.await();
    }

    /**
     * 超过核心线程数，优先进入队列
     * @throws InterruptedException
     */
    @Test
    public void testLinkedBlockingDeque() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10));

        // 保证@test单元测试主线程等待
        CountDownLatch countDownLatch = new CountDownLatch(8);

        for (int i = 0; i < 8; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                    log.info("thread name:{},active:{},queue:{},",Thread.currentThread().getName(),
                            executor.getActiveCount(), executor.getQueue().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * 超过核心线程数，优先进入队列
     * @throws InterruptedException
     */
    @Test
    public void testArrayBlockingQueue() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));

        // 保证@test单元测试主线程等待
        CountDownLatch countDownLatch = new CountDownLatch(8);

        for (int i = 0; i < 8; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                    log.info("thread name:{},active:{},queue:{},",Thread.currentThread().getName(),
                            executor.getActiveCount(), executor.getQueue().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * 超过核心线程数，RejectedExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSynchronousQueue() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 5, TimeUnit.SECONDS,
                new SynchronousQueue<>(true));

        // 保证@test单元测试主线程等待
        CountDownLatch countDownLatch = new CountDownLatch(8);

        for (int i = 0; i < 8; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                    log.info("thread name:{},active:{},queue:{},",Thread.currentThread().getName(),
                            executor.getActiveCount(), executor.getQueue().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * AbortPolicy
     * 默认:当任务添加到线程池中被拒绝时，它将抛出RejectedExecutionException异常
     * @throws InterruptedException
     */
    @Test
    public void testAbortPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS,
                new SynchronousQueue<>(true));

        // 保证@test单元测试主线程等待
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            executor.execute(() -> {
                log.info("thread name:{},active:{}",Thread.currentThread().getName(),
                        executor.getActiveCount());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * CallerRunsPolicy
     * 当任务添加到线程池中被拒绝时，会使用调用线程池的Thread线程对象处理被拒绝的任务。
     * @throws InterruptedException
     */
    @Test
    public void testCallerRunsPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS,
                new SynchronousQueue<>(true), new ThreadPoolExecutor.CallerRunsPolicy());
        // 保证@test单元测试主线程等待
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            executor.execute(() -> {
                log.info("thread name:{},active:{}",Thread.currentThread().getName(),
                        executor.getActiveCount());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * DiscardOldestPolicy
     * 当任务添加到线程池中被拒绝时，线程池会放弃等待队列中最旧的未处理任务，然后将被拒绝的任务添加到等待队列中。
     * @throws InterruptedException
     */
    @Test
    public void testDiscardOldestPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1), new ThreadPoolExecutor.DiscardOldestPolicy());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        for (int i = 0; i < 2; i++) {
            executor.execute(() -> {
                countDownLatch.countDown();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name:{},active:{}",Thread.currentThread().getName(),
                        executor.getActiveCount());
            });
        }
        countDownLatch.await();
        executor.execute(() -> {
            log.info("haha");
            countDownLatch2.countDown();
        });
        countDownLatch2.await();
    }

    /**
     * DiscardPolicy：当任务添加到线程池中被拒绝时，线程池将丢弃被拒绝的任务。
     * @throws InterruptedException
     */
    @Test
    public void testDiscardPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1), new ThreadPoolExecutor.DiscardPolicy());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            executor.execute(() -> {
                countDownLatch.countDown();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name:{},active:{}",Thread.currentThread().getName(),
                        executor.getActiveCount());
                countDownLatch2.countDown();
            });
        }
        countDownLatch.await();
        executor.execute(() -> {
            log.info("haha");
        });
        countDownLatch2.await();
    }

    /**
     * 在线程池ThreadPoolExecutor类中重写这两个方法可以对线程池中执行的线程对象实现监控。
     */
    @Test
    public void extendExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10)){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                log.info("thread:{} begin", t.getName());
            }
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (t == null && r instanceof Future<?>) {
                    try {
                        Object result = ((Future) r).get();
                        log.info("execute success:{}", result);
                    } catch (InterruptedException e) {
                        t = e;
                    } catch (ExecutionException e) {
                        t = e.getCause();
                    }
                }
                if (t != null) {
                    log.error("execute error:{}", t);
                }
            }
        };
        final int count = 1;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        executor.execute(() -> {
            log.info("haha");
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    /**
     * 方法remove(Runnable)可以删除尚未被执行的Runnable任务。
     */
    @Test
    public void testRemoveRunnable() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 5, TimeUnit.SECONDS,
                new SynchronousQueue<>(true), (r, executor1) -> {
                    log.info("rejected...");
                    executor1.remove(r);
                });

        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
