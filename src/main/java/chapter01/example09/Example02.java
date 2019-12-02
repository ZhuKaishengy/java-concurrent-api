package chapter01.example09;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description:
 *  tryAcquire(long timeout, TimeUnit unit)的使用
 *  tryAcquire(int permits, long timeout, TimeUnit unit)的使用
 */
@Slf4j
public class Example02 {

    private static final int PERMIT_COUNT = 5;
    public static Semaphore semaphore = new Semaphore(PERMIT_COUNT, true);

    public void service() {
        try {
            String threadName = Thread.currentThread().getName();
            if (semaphore.tryAcquire(PERMIT_COUNT, 3, TimeUnit.SECONDS)) {
                // 适当加一些复杂业务
                Thread.sleep(3000);
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
        Example02 semaphoreHelloWorld = new Example02();
        Thread threadA = new Thread(semaphoreHelloWorld::service);
        threadA.setName("A");
        Thread threadB = new Thread(semaphoreHelloWorld::service);
        threadB.setName("B");
        threadA.start();
        threadB.start();
    }
}
