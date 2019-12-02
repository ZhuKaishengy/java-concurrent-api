package chapter01.example08;

import chapter01.common.Safe;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: 公平信号量、非公平信号量
 */
@Slf4j
public class SemaphoreFair {

    /**
     * 非公平信号量运行的效果是线程启动的顺序与调用semaphore.acquire()的顺序无关，也就是线程先启动了并不代表先获得许可。
     * 公平信号量运行的效果是线程启动的顺序与调用semaphore.acquire()的顺序有关，也就是先启动的线程优先获得许可。
     * 多次尝试，会出现结果
     */
    private static final boolean FAIR = false;
    public static Semaphore semaphore = new Semaphore(1, FAIR);

    @Safe
    public void service() {
        try {
            semaphore.acquire();
            String threadName = Thread.currentThread().getName();
            log.info("current thread name {}, time {}", threadName, new Date());
            semaphore.release();
        } catch (InterruptedException e) {
            log.error("error occurs:{}", e);
        }
    }

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) {
        SemaphoreFair semaphoreHelloWorld = new SemaphoreFair();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {
                log.info("{}线程启动了", Thread.currentThread().getName());
                semaphoreHelloWorld.service();
            });
            thread.start();
        }

    }
}
