package chapter07.example02;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 16:32
 * @Description:
 */
@Slf4j
public class InvokeAllTest {

    /**
     * 方法invokeAll(Collection tasks)全正确
     */
    @Test
    public void test1() {
        int count = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(new InvokeAll1Callable(i));
        }
        try {
            List<Future<String>> futures = executorService.invokeAll(list);
            for (Future<String> future : futures) {
                log.info(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("error occur:{}", e);
        }
    }

    /**
     * 方法invokeAll(Collection tasks)快的正确慢的异常
     * invokeAll()方法对Callable抛出去的异常是可以处理的，在main()方法中直接进入了catch语句块，所以导致后面的无法执行。
     */
    @Test
    public void test2() {
        int count = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(new InvokeAll2Callable(i));
        }
        try {
            List<Future<String>> futures = executorService.invokeAll(list);
            for (Future<String> future : futures) {
                log.info(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("error occur:{}", e.toString());
        }
    }

    /**
     * 方法invokeAll(Collection tasks, long timeout, TimeUnit unit)先慢后快
     *
     */
    @Test
    public void test3() throws InterruptedException {
        int count = 5;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(new InvokeAll1Callable(i));
        }
        try {
            List<Future<String>> futures = executorService.invokeAll(list, 2, TimeUnit.SECONDS);
//            List<Future<String>> futures = executorService.invokeAll(list, 5, TimeUnit.SECONDS);
            for (Future<String> future : futures) {
                log.info(future.get());
            }
        } catch (InterruptedException | ExecutionException  | CancellationException e) {
            log.error("error occur:{}", e.toString());
            // 用来等待worker线程的执行状态
            Thread.sleep(10000);
        }
    }
}
