package chapter10.example04;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:12
 * @Description:
 */
@Slf4j
public class CopyOnWriteArraySetTest {

    private Set<Integer> hashSet = new HashSet<>();
    private Set<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    @Data
    @AllArgsConstructor
    class MyCallable implements Callable<String> {

        private Integer begin;
        private Integer end;
        private Set<Integer> set;

        @Override
        public String call() throws Exception {
            for (int i = begin; i < end; i++) {
                set.add(i);
                Thread.sleep(100);
            }
            return "success";
        }
    }

    /**
     * hashset 线程不安全
     */
    @Test
    public void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(new MyCallable(0, 50, hashSet));
        tasks.add(new MyCallable(50, 100, hashSet));
        executorService.invokeAll(tasks);
        log.info("count:{}", hashSet.size());
        for (Integer item : hashSet) {
            log.info("item:{}", item);
        }
    }

    /**
     * hashset 线程不安全
     */
    @Test
    public void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(new MyCallable(0, 50, copyOnWriteArraySet));
        tasks.add(new MyCallable(50, 100, copyOnWriteArraySet));
        executorService.invokeAll(tasks);
        log.info("count:{}", copyOnWriteArraySet.size());
        for (Integer item : copyOnWriteArraySet) {
            log.info("item:{}", item);
        }
    }
}
