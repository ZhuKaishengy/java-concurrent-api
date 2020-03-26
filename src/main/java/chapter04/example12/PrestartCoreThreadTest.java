package chapter04.example12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/25 18:02
 * @Description: 可以预启动一个或多个核心线程，若预启动的数<corepoolsize，在任务执行的时候，还是会优先自己创建，不使用预创建的。。。
 */
@Slf4j
public class PrestartCoreThreadTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        CountDownLatch countDownLatch = new CountDownLatch(1);

        log.info("init pool size:{}", executor.getPoolSize());
        if (executor.prestartCoreThread()) {
            log.info("pre start pool size:{}", executor.getPoolSize());
        }
        executor.execute(() -> {
            log.info("thread name :{} run ..", Thread.currentThread().getName());
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("thread run pool size:{}", executor.getPoolSize());
        countDownLatch.countDown();
        executor.prestartAllCoreThreads();
        log.info("all start pool size:{}", executor.getPoolSize());
        executor.shutdown();
    }

}
