package chapter01.example06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: 获取可用的许可数、重置许可数
 */
@Slf4j
public class SemaphoreHelloWorld {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        semaphore.acquireUninterruptibly();
        log.info("available permits:{}", semaphore.availablePermits());
        log.info("drain permits:{}, available permits:{}", semaphore.drainPermits(), semaphore.availablePermits());
        log.info("available permits:{}", semaphore.availablePermits());
    }
}
