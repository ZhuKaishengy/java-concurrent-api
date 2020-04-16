package chapter06.example01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 10:40
 * @Description: 使用CompletionService解决Future的阻塞性
 */
@Slf4j
public class SimpleTest {

    /**
     * 前面先执行的任务一旦耗时很多，则后面的任务调用get()方法就呈阻塞状态，也就是排队进行等待，大大影响运行效率。
     * 也就是 主线程并不能保证首先获得的是最先完成任务的返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test01() throws ExecutionException, InterruptedException {
        int jobCount = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>(jobCount);
        for (int i = jobCount; i > 1; i--) {
            Future<String> future = executorService.submit(new SimpleCallable(i));
            futures.add(future);
        }
        for (Future<String> future : futures) {
            String result = future.get();
            log.info(result);
        }
    }

    /**
     * 使用 ExecutorCompletionService#take() 取消阻塞
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test02() throws InterruptedException, ExecutionException {
        int jobCount = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<>(executorService);
        for (int i = jobCount; i > 1; i--) {
            executorCompletionService.submit(new SimpleCallable(i));
        }
        for (int i = 1; i < jobCount; i++) {
            Future<String> future = executorCompletionService.take();
            String result = future.get();
            log.info(result);
        }
    }

}
