package chapter03.example08;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 10:33
 * @Description:
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) {

        Phaser phaser = new Phaser(2);
        // 等待超时
        Thread thread = MainTest.newThread(phaser);
        thread.start();
        // phase
        phaser.arrive();
        phaser.arrive();
        // 被中断
        thread.interrupt();
    }

    private static Thread newThread(Phaser phaser) {

        return new Thread(() -> {
            try {
                log.info("begin...");
                phaser.awaitAdvanceInterruptibly(0, 3, TimeUnit.SECONDS);
                log.info("end...");
            } catch (InterruptedException e) {
                log.error("interrupted...");
            } catch (TimeoutException e) {
                log.error("timeout...");
            }
        });
    }
}
