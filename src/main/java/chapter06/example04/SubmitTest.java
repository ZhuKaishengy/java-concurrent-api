package chapter06.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 13:20
 * @Description: 方法`Future<V> submit(Runnable task, V result)`的测试
 */
@Slf4j
public class SubmitTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        // 参数V是submit()方法的返回值。
        Future<String> future = completionService.submit(() -> log.info("haha"), "xixi");
        String result = future.get();
        log.info(result);
        executorService.shutdown();
    }
}
