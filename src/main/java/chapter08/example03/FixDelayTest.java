package chapter08.example03;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/17 15:44
 * @Description:
 */
@Slf4j
public class FixDelayTest {

    private ScheduledExecutorService scheduledExecutorService;

    {
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
    }

    /**
     * 上一个执行任务线程结束后，间隔多久执行下一次任务
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        log.info("job created...");
        ScheduledFuture<?> future1 = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log.info("job1 run...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("job1 stop...");
        }, 0,1, TimeUnit.SECONDS);

        ScheduledFuture<?> future2 = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log.info("job2 run...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("job2 stop...");
        }, 0,5, TimeUnit.SECONDS);


        Object o1 = future1.get();
        Object o2 = future2.get();
        log.info("result1:{},result2:{}", o1, o2);
    }

}
