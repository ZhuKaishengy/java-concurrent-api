package chapter01.example05;

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
public class ThreadInterrupted {


    public static Semaphore semaphore = new Semaphore(1);

    @Safe
    public void service() {
        try {
            semaphore.acquire();
            String threadName = Thread.currentThread().getName();
            log.info("current thread name {}, start time {}", threadName, new Date());
            for (int i = 0; i < Integer.MAX_VALUE / 50; i++) {
                // 模拟费时操作
                Math.random();
            }
            log.info("current thread name {}, end time {}", threadName, new Date());
            semaphore.release();
        } catch (InterruptedException e) {
            log.error("corrent thread name {},  error occurs:{}", Thread.currentThread().getName(), e);
        }
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) throws InterruptedException {
        ThreadInterrupted semaphoreHelloWorld = new ThreadInterrupted();
        Thread threadA = new Thread(semaphoreHelloWorld::service);
        threadA.setName("A");
        Thread threadB = new Thread(semaphoreHelloWorld::service);
        threadB.setName("B");
        threadA.start();
        threadB.start();
        Thread.sleep(1000);
        threadB.interrupt();
        log.info("main thread interrupt thread B");
    }
}
