package chapter05.example05;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 16:33
 * @Description: 异常处理
 */
@Slf4j
public class ExceptionTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> Integer.parseInt("a"));
        try {
            Integer result = future.get();
            log.info("result:{}", result);
        } catch (InterruptedException e) {
            log.error("InterruptedException ...");
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.error("ExecutionException ...");
        } finally {
            executorService.shutdown();
        }
    }
}
