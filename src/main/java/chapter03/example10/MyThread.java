package chapter03.example10;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 11:04
 * @Description:
 */
@Slf4j
public class MyThread extends Thread {

    private Phaser phaser;

    public MyThread(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        log.info("thread:{} begin ...", Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        log.info("thread:{} end ...", Thread.currentThread().getName());
    }
}
