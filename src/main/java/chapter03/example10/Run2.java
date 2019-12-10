package chapter03.example10;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 11:06
 * @Description:
 * 此实验说明运行的时机是可以通过逻辑控制的，主要的原理就是计数+1，然后通过逻辑代码的方式来决定线程是否继续向下运行
 */
@Slf4j
public class Run2 {
    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(3);
        // add one party
        phaser.register();
        for (int i = 0; i < 3; i++) {
            MyThread myThread = new MyThread(phaser);
            myThread.start();
        }
        Thread.sleep(2000);
        log.info("deregister...");
        phaser.arriveAndDeregister();
    }
}
