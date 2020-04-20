package chapter08.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/17 17:18
 * @Description:
 */
@Slf4j
public class QueueTest {

    /**
     * 方法getQueue()的作用是取得队列中的任务，而这些任务是未来将要运行的，正在运行的任务不在此队列中。
     * 使用scheduleAtFixedRate()和scheduleWithFixedDelay()两个方法实现周期性执行任务时，未来欲执行的任务都是放入此队列中。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(2);
        executorService.scheduleAtFixedRate(() -> log.info("thread :{} run...", Thread.currentThread().getName()),
                0, 3, TimeUnit.SECONDS);
        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(() -> log.info("thread :{} run...", Thread.currentThread().getName()),
                0, 2, TimeUnit.SECONDS);

        while (true) {
            BlockingQueue<Runnable> queue = executorService.getQueue();
            log.info("queue size:{}", queue.size());
            if (queue.size() == 2) {
                // 注意：remove()方法的参数是ScheduledFuture数据类型
                queue.remove(future);
            }
            Thread.sleep(1000);
        }
    }
}
