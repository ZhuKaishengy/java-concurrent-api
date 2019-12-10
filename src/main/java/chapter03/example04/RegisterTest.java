package chapter03.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 17:37
 * @Description:
 * 类Phaser的getRegisteredParties()方法和register()测试
 * 类Phaser的bulkRegister()方法测试
 * 类Phaser的getArrivedParties()和getUnarrivedParties()方法测试
 */
@Slf4j
public class RegisterTest {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(3);
        // 3
        log.info("current registered parties:{}", phaser.getRegisteredParties());
        phaser.register();
        // 4
        log.info("current registered parties:{}", phaser.getRegisteredParties());
        phaser.register();
        // 5
        log.info("current registered parties:{}", phaser.getRegisteredParties());
        phaser.bulkRegister(5);
        // 10
        log.info("current registered parties:{}", phaser.getRegisteredParties());

        // 0 10
        log.info("current arrived parties:{}, unarrived parties:{}", phaser.getArrivedParties(), phaser.getUnarrivedParties());

        new Thread(phaser::arriveAndAwaitAdvance).start();
        Thread.sleep(2000);
        // 1 9
        log.info("current arrived parties:{}, unarrived parties:{}", phaser.getArrivedParties(), phaser.getUnarrivedParties());
    }
}
