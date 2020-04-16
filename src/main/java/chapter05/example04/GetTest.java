package chapter05.example04;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 16:26
 * @Description:
 */
@Slf4j
public class GetTest {

    /**
     * 在等待时间内返回
     */
    @Test
    public void test01() throws InterruptedException, ExecutionException, TimeoutException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        });
        String result = future.get(2, TimeUnit.SECONDS);
        log.info("result:{}", result);
    }
    /**
     * 超时返回
     * TimeoutException
     */
    @Test
    public void test02() throws InterruptedException, ExecutionException, TimeoutException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "success";
        });
        String result = future.get(1, TimeUnit.SECONDS);
        log.info("result:{}", result);
    }
}
