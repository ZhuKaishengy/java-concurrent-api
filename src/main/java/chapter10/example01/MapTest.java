package chapter10.example01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/21 18:44
 * @Description:
 */
@Slf4j
public class MapTest {

    private Map<Integer, Integer> map = new HashMap<>();
    private Map<Integer, Integer> table = new Hashtable<>();
    private Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    @AllArgsConstructor
    @Data
    class MyCallable implements Callable<String> {

        private Integer begin;
        private Integer end;
        private Map<Integer, Integer> map;

        @Override
        public String call() throws InterruptedException {
            for (int i = begin; i < end; i++) {
                Thread.sleep(100);
                map.put(i, i);
            }
            return "success";
        }
    }

    /**
     * HashMap线程不安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test1() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new MyCallable(0, 50, map));
        completionService.submit(new MyCallable(50, 100, map));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
            log.info("k:{},v:{}", i, map.get(i));
        }
    }
    /**
     * HashTable线程安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test2() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new MyCallable(0, 50, table));
        completionService.submit(new MyCallable(50, 100, table));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
            log.info("k:{},v:{}", i, table.get(i));
        }
        table.forEach((k, v) -> table.remove(k));
    }

    /**
     * concurrentHashMap线程不安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test3() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(new MyCallable(0, 50, concurrentHashMap));
        completionService.submit(new MyCallable(50, 100, concurrentHashMap));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
            log.info("k:{},v:{}", i, concurrentHashMap.get(i));
        }
        concurrentHashMap.forEach((k, v) -> concurrentHashMap.remove(k));
    }

}
