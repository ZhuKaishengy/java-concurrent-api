package chapter01.example01;

import chapter01.common.Safe;
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
     * 类Semaphore的构造函数参数permits是许可的意思，代表同一时间内，最多允许多少个线程同时执行acquire()和release()之间的代码。
     * 无参方法acquire()的作用是使用1个许可，是减法操作。
     */
    public static Semaphore semaphore = new Semaphore(1);

    @Safe
    public void service() {
        try {
            semaphore.acquire();
            String threadName = Thread.currentThread().getName();
            log.info("current thread name {}, start time {}", threadName, new Date());
            Thread.sleep(1000);
            log.info("current thread name {}, end time {}", threadName, new Date());
            semaphore.release();
        } catch (InterruptedException e) {
            log.error("error occurs:{}", e);
        }
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) {
        SemaphoreHelloWorld semaphoreHelloWorld = new SemaphoreHelloWorld();
        Thread threadA = new Thread(semaphoreHelloWorld::service);
        threadA.setName("A");
        Thread threadB = new Thread(semaphoreHelloWorld::service);
        threadB.setName("B");
        Thread threadC = new Thread(semaphoreHelloWorld::service);
        threadC.setName("C");
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
