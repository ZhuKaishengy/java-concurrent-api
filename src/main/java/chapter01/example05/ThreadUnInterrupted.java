package chapter01.example05;

import chapter01.common.Safe;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: 等待进入acquire()方法的线程无法被中断
 */
@Slf4j
public class ThreadUnInterrupted {


    public static Semaphore semaphore = new Semaphore(1);

    @Safe
    public void service() {
        semaphore.acquireUninterruptibly();
        String threadName = Thread.currentThread().getName();
        log.info("current thread name {}, start time {}", threadName, new Date());
        for (int i = 0; i < Integer.MAX_VALUE / 50; i++) {
            // 模拟费时操作
            Math.random();
        }
        log.info("current thread name {}, end time {}", threadName, new Date());
        semaphore.release();
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) throws InterruptedException {
        ThreadUnInterrupted semaphoreHelloWorld = new ThreadUnInterrupted();
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
