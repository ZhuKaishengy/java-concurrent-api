package chapter08.example02;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/17 15:25
 * @Description:
 */
@Slf4j
public class FixRateTest {

    private ScheduledExecutorService scheduledExecutorService;

    {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * 正常情况，到任务执行时间点有可用worker线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        log.info("job created...");
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("job run...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("job stop...");
        }, 0, 2, TimeUnit.SECONDS);
        Object o = scheduledFuture.get();
        log.info("result:{}", o);
    }

    /**
     * 特殊情况，到任务执行时间点没有可用worker线程
     * 一直有挤压的任务，会一直执行
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        log.info("job created...");
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("job run...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("job stop...");
        }, 0, 2, TimeUnit.SECONDS);
        Object o = scheduledFuture.get();
        log.info("result:{}", o);
    }
}
