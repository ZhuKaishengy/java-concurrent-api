package chapter04.example11;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/25 17:44
 * @Description: allowCoreThreadTimeOut 允许核心线程超时销毁
 */
@Slf4j
public class TimeOutTest {

    @Test
    public void testAllowCoreThreadTimeOut() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        executor.allowCoreThreadTimeOut(true);

        for (int i = 0; i < 3; i++) {
            executor.execute(() -> log.info("thread name:{} run...", Thread.currentThread().getName()));
        }
        Thread.sleep(8000);
        int corePoolSize = executor.getPoolSize();
        log.info("final core:{}", corePoolSize);
    }

    @Test
    public void testNotAllowCoreThreadTimeOut() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        executor.allowCoreThreadTimeOut(false);

        for (int i = 0; i < 3; i++) {
            executor.execute(() -> log.info("thread name:{} run...", Thread.currentThread().getName()));
        }
        Thread.sleep(8000);
        int corePoolSize = executor.getPoolSize();
        log.info("final core:{}", corePoolSize);
    }
}
