package chapter03.example09;

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

    public static void main(String[] args) throws InterruptedException {

        Phaser phaser = new Phaser(2);
        Thread thread = new Thread(() -> {
            log.info("start...");
            phaser.awaitAdvance(0);
            log.info("end...");
        });
        thread.start();
        log.info("isTerminated:{}", phaser.isTerminated());
        phaser.forceTermination();
        Thread.sleep(1000);
        log.info("isTerminated:{}", phaser.isTerminated());
    }
}
