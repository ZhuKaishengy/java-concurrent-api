package chapter03.example02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 15:32
 * @Description: 方法arriveAndDeregister()的作用是使当前线程退出计数，并且使parties值减1。
 */
@Slf4j
public class MyService {

    private Phaser phaser;

    public MyService(Phaser phaser) {
        this.phaser = phaser;
    }

    private static final String threadName = Thread.currentThread().getName();

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

    public void deRegist() {
        String threadName = Thread.currentThread().getName();
        log.info("threadName:{}, arrived begin deregister, current register parties:{}, current arrived parties:{}...",
                threadName, phaser.getRegisteredParties(), phaser.getArrivedParties());
        phaser.arriveAndDeregister();
        log.info("threadName:{}, arrived end deregister, current register parties:{}, current arrived parties:{}...",
                threadName, phaser.getRegisteredParties(), phaser.getArrivedParties());
    }
}
