package chapter08.example05;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/17 17:38
 * @Description:
 */
@Slf4j
public class OtherMethodTest {

    /**
     * 方法setExecuteExistingDelayedTasksAfterShutdownPolicy()的作用是当对Scheduled-ThreadPoolExecutor执行了shutdown()方法时，任务是否继续运行，
     * 默认值是true，也就是当调用了shutdown()方法时任务还是继续运行，当使用setExecuteExistingDelayedTasksAfterShutdownPolicy(false)时任务不再运行。
     * 方法setExecuteExistingDelayedTasksAfterShutdownPolicy()可以与schedule()和shutdown()方法联合使用
     */
    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
        scheduledThreadPoolExecutor.schedule(() -> {
            log.info("worker run ...");
            countDownLatch.countDown();
        },1, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.shutdown();
        countDownLatch.await();
    }

    /**
     * scheduleAtFixedRate()和scheduleWithFixedDelay()方法与setContinueExistingPeriodicTasksAfterShutdownPolicy()方法联合使用
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            log.info("worker run ...");
            countDownLatch.countDown();
        },0, 1, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            log.info("worker run ...");
            countDownLatch.countDown();
        },0, 1, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.shutdown();
        countDownLatch.await();
    }

    @Test
    public void test3() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        // 方法setRemoveOnCancelPolicy(boolean)的作用设定是否将取消后的任务从队列中清除。
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        ScheduledFuture<?> scheduledFuture1 = scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            log.info("worker1 run ...");
        }, 0, 2, TimeUnit.SECONDS);

        BlockingQueue<Runnable> queue = scheduledThreadPoolExecutor.getQueue();
        log.info("queue1 size:{}", queue.size());
        // 方法cancel(boolean)的作用设定是否取消任务
        scheduledFuture1.cancel(true);
        log.info("queue2 size:{}", queue.size());
    }
}
