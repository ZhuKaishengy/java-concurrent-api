package chapter05.example03;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 15:53
 * @Description:
 */
@Slf4j
public class CancelTest {

    /**
     * 正常任务执行中中断
     * cancel:true
     */
    @Test
    public void test01() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                log.info("doing...");
                Thread.sleep(2000);
            }
            log.info("done...");
            return "done";
        });
        boolean cancel = future.cancel(true);
        log.info("cancel:{}",cancel);
    }

    /**
     * 任务执行完进行中断
     * cancel:false
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test02() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            for (int i = 0; i < 10; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("error occur");
                    return "error";
                }
                log.info("doing...");
                Thread.sleep(2000);
            }
            log.info("done...");
            return "done";
        });
        String result = future.get();
        log.info("result:{}", result);
        boolean cancel = future.cancel(true);
        log.info("cancel:{}",cancel);
    }

    @Test
    public void test03() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            while (true) {
                log.info("doing...");
            }
        });
        Thread.sleep(1000);
        boolean cancel = future.cancel(true);
        log.info("cancel:{}",cancel);
    }
}
