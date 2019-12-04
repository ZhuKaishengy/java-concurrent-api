package chapter01.example07;

import common.Safe;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: 等待进入acquire()方法的线程被中断
 */
@Slf4j
public class QueueLength {


    public static Semaphore semaphore = new Semaphore(1);

    @Safe
    public void service() {
        try {
            semaphore.acquire();
            String threadName = Thread.currentThread().getName();
            log.info("current thread name {}, start time {}", threadName, new Date());
            int queueLength = semaphore.getQueueLength();
            boolean hasQueuedThreads = semaphore.hasQueuedThreads();
            log.info("是否有线程等待许可:{}, 排队的线程数{}", hasQueuedThreads, queueLength);
            log.info("current thread name {}, end time {}", threadName, new Date());
            semaphore.release();
        } catch (InterruptedException e) {
            log.error("corrent thread name {},  error occurs:{}", Thread.currentThread().getName(), e);
        }
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) throws InterruptedException {
        QueueLength queueLength = new QueueLength();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(queueLength::service);
            threads[i].start();
        }
    }
}
