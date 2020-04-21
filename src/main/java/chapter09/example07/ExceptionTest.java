package chapter09.example07;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/21 17:59
 * @Description:
 */
@Slf4j
public class ExceptionTest {

    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Void> future = forkJoinPool.submit(new RecursiveAction() {
            @Override
            protected void compute() {
                try {
                    int a = 1 / 0;
                } catch (Exception e) {
                    log.error("ex:{}", e.toString());
                    countDownLatch.countDown();
                    throw e;
                }
            }
        });
        log.info("completedNormally:{},completedAbnormally:{}", future.isCompletedNormally(), future.isCompletedAbnormally());
        countDownLatch.await();
        log.info("completedNormally:{},completedAbnormally:{}", future.isCompletedNormally(), future.isCompletedAbnormally());
    }
}
