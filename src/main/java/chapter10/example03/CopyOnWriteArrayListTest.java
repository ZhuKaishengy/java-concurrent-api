package chapter10.example03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:03
 * @Description:
 */
@Slf4j
public class CopyOnWriteArrayListTest {

    private List<Integer> list = new ArrayList<>();
    private List<Integer> vector = new Vector<>();
    private List<Integer> copyList = new CopyOnWriteArrayList<>();

    @AllArgsConstructor
    @Data
    class MyCallable implements Callable<String> {

        private Integer begin;
        private Integer end;
        private List<Integer> list;

        @Override
        public String call() throws InterruptedException {
            for (int i = begin; i < end; i++) {
                list.add(i);
                Thread.sleep(100);
            }
            return "success";
        }
    }

    /**
     * ArrayList线程不安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test1() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new MyCallable(0, 50, list));
        completionService.submit(new MyCallable(50, 100, list));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
            log.info("item:{}", list.get(i));
        }
    }

    /**
     * Vector线程安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test2() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new MyCallable(0, 50, vector));
        completionService.submit(new MyCallable(50, 100, vector));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
            log.info("item:{}", vector.get(i));
        }
    }

    /**
     * CopyOnWriteArrayList线程安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test3() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new MyCallable(0, 50, copyList));
        completionService.submit(new MyCallable(50, 100, copyList));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
            log.info("item:{}", copyList.get(i));
        }
    }
}
