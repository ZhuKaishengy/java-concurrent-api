package chapter03.example01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 15:32
 * @Description:
 */
@Slf4j
public class MyService {

    private Phaser phaser;

    public MyService(Phaser phaser) {
        this.phaser = phaser;
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            log.info("threadName:{}, first time begin...", threadName);
            phaser.arriveAndAwaitAdvance();
            log.info("threadName:{}, first time end...", threadName);
            Thread.sleep((int) (Math.random() * 1000));
            log.info("threadName:{}, second time begin...", threadName);
            phaser.arriveAndAwaitAdvance();
            log.info("threadName:{}, second time end...", threadName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
