package chapter04.example01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:07
 * @Description: 创建无界线程池,存放线程个数是理论上的Integer.MAX_VALUE最大值
 */
@Slf4j
public class CachedThreadPoolTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 相位器，用于线程关闭
        Phaser phaser = new Phaser(10);

        for (int i = 0; i < 10; i++) {

            executorService.execute(() -> {
                try {
                    log.info("thread:{}, start...", Thread.currentThread().getName());
                    Thread.sleep(1000);
                    log.info("thread:{}, end...", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    phaser.arrive();
                }
            });
        }
        phaser.awaitAdvance(0);
        executorService.shutdown();
    }
}
