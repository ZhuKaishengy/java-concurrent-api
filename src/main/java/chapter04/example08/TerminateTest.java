package chapter04.example08;

import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/25 15:36
 * @Description:
 */
@Slf4j
public class TerminateTest {

    /**
     * isTerminating 正在关闭
     * isTerminated 关闭完了
     * @throws InterruptedException
     */
    @Test
    public void testTerminatingAndTerminated() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Phaser phaser = new Phaser(2);
        executor.execute(() -> {
            log.info("任务一开始执行。。。");
            phaser.arriveAndAwaitAdvance();
            log.info("任务一执行完毕。。。");
            phaser.forceTermination();
        });
        executor.shutdown();
        log.info("terminating:{} \t terminated:{}", executor.isTerminating(), executor.isTerminated());
        phaser.arrive();
        phaser.awaitAdvance(1);
        Thread.sleep(1);
        log.info("terminating:{} \t terminated:{}", executor.isTerminating(), executor.isTerminated());
    }

    /**
     * 查看在指定的时间之间，线程池是否已经终止工作
     * @throws InterruptedException
     */
    @Test
    public void testAwaitTermination() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(30));
        Phaser phaser = new Phaser(2);
        executor.execute(() -> {
            log.info("任务开始执行。。。");
            phaser.arriveAndAwaitAdvance();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("任务执行完毕。。。");
        });
        phaser.arriveAndAwaitAdvance();
        executor.shutdown();
        boolean b = executor.awaitTermination(1, TimeUnit.SECONDS);
        if (b) {
            log.info("关的真快。。。");
        } else {
            log.info("关的真慢。。。");
        }
    }
}
