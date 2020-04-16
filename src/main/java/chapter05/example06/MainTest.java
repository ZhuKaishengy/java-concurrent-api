package chapter05.example06;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/27 10:08
 * @Description: 测试callable接口get阻塞问题
 */
@Slf4j
public class MainTest {

    /**
     * 前面先执行的任务一旦耗时很多，则后面的任务调用get()方法就呈阻塞状态，也就是排队进行等待，大大影响运行效率。
     * 也就是 主线程并不能保证首先获得的是最先完成任务的返回值
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<String>> futures = new ArrayList<>(5);

        for (int i = 10; i > 5; i--) {
            Future<String> future = executorService.submit(new MyCallable(i));
            futures.add(future);
        }
        log.info("before get...");
        for (Future<String> future : futures) {
            String result = future.get();
            log.info("result:{}", result);
        }
        executorService.shutdown();
    }
}
