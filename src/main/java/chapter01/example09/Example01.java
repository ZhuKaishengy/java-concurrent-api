package chapter01.example09;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: tryAcquire方法线程非阻塞
 */
@Slf4j
public class Example01 {

    private static final int PERMIT_COUNT = 5;
    public static Semaphore semaphore = new Semaphore(PERMIT_COUNT, true);

    public void service() {
        try {
            String threadName = Thread.currentThread().getName();
            if (semaphore.tryAcquire(PERMIT_COUNT)) {
                log.info("access current thread name {}, time {}", threadName, new Date());
            } else {
                log.info("unAccess current thread name {}, time {}", threadName, new Date());
            }
        } catch (Exception e) {
            log.error("error occurs:{}", e);
        } finally {
            semaphore.release(PERMIT_COUNT);
        }
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) {
        Example01 semaphoreHelloWorld = new Example01();
        Thread threadA = new Thread(semaphoreHelloWorld::service);
        threadA.setName("A");
        Thread threadB = new Thread(semaphoreHelloWorld::service);
        threadB.setName("B");
        threadA.start();
        threadB.start();
    }
}
