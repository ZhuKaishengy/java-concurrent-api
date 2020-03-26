package chapter04.example09;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/25 16:46
 * @Description:
 */
@Slf4j
public class ThreadFactoryTest {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 创建一个线程池的时候，可以指定线程工厂，在这儿可以给线程注入一些属性，捕获异常啥的
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30), new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                int val = count.getAndIncrement();
                Thread th = new Thread(r);
                th.setName("th-" + val);
                th.setUncaughtExceptionHandler((t, e) -> log.info("error occur thread name:{} error:{}", t.getName(), e));
                return th;
            }
        });

        CountDownLatch countDownLatch = new CountDownLatch(2);
        threadPoolExecutor.execute(() -> {
            log.info("thread name:{}", Thread.currentThread().getName());
            countDownLatch.countDown();
        });
        threadPoolExecutor.execute(() -> {
            countDownLatch.countDown();
            throw new RuntimeException("hhh");
        });
        countDownLatch.await();
        threadPoolExecutor.shutdown();
    }
}
