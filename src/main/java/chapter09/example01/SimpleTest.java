package chapter09.example01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/20 11:06
 * @Description:
 */
@Slf4j
public class SimpleTest {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Void> task = forkJoinPool.submit(new RecursiveAction() {
            @Override
            protected void compute() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("hahaha");
            }
        });
        // RecursiveAction执行的任务是具有无返回值的，get()方法只是为了线程同步
        Void aVoid = task.get();
        log.info("result:{}", aVoid);
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Void> task = forkJoinPool.submit(new MyRecursiveAction(1, 10));
        Void aVoid = task.get();
        log.info("result:{}", aVoid);
    }
}
