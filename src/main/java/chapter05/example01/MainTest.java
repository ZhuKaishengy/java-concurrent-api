package chapter05.example01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 14:42
 * @Description: 常见使用
 */
@Slf4j
public class MainTest {

    /**
     *  executor.submit(Callable)
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "hello";
        });
        // 方法get()具有阻塞特性。
        String result = future.get();
        log.info("result:{}", result);
        executor.shutdown();
    }

    @Test
    public void testRunnable() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Future<?> future = executor.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("xixi");
        });
        // isDone 非阻塞
        boolean isDown = future.isDone();
        log.info("isDown:{}", isDown);
        // get 阻塞
        Object result = future.get();
        log.info("get:{}", result);
    }

}
