package chapter01.example03;

import chapter01.common.Unsafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: 初识semaphore
 */
@Slf4j
public class SemaphoreHelloWorld {

    /**
     * 一共有10个许可，每次执行semaphore. acquire(2)；代码时耗费掉2个，所以10/2=5，
     * 说明同一时间只有5个线程允许执行acquire()和release()之间的代码。
     */
    public static Semaphore semaphore = new Semaphore(10);

    @Unsafe
    public void service() {
        try {
            semaphore.acquire(2);
            String threadName = Thread.currentThread().getName();
            log.info("current thread name {}, start time {}", threadName, new Date());
            int pause = (int) (Math.random() * 10000);
            log.info("current thread name {}, pause time {}s", threadName, pause / 1000);
            Thread.sleep(pause);
            log.info("current thread name {}, end time {}", threadName, new Date());
            semaphore.release(2);
        } catch (InterruptedException e) {
            log.error("error occurs:{}", e);
        }
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) {
        SemaphoreHelloWorld semaphoreHelloWorld = new SemaphoreHelloWorld();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(semaphoreHelloWorld::service);
            threads[i].start();
        }
    }
}
