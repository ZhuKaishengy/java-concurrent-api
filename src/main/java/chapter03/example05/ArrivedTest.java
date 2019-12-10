package chapter03.example05;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 17:57
 * @Description:
 * 方法arrive()的作用是使parties值加1，并且不在屏障处等待，直接向下面的代码继续运行，并且Phaser类有计数重置功能。
 * 当计数不足时，线程A和B依然呈等待状态。
 */
@Slf4j
public class ArrivedTest {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(2) {
            protected boolean onAdvance(int phase, int registeredParties) {
                log.info("achieve phaser barrier");
                return super.onAdvance(phase, registeredParties);
            }
        };
        // 0 2 0
        log.info("A1 getPhase:{}, getRegisteredParties:{}, getArrivedParties:{}",
                phaser.getPhase(), phaser.getRegisteredParties(), phaser.getArrivedParties());
        phaser.arrive();
        // 0 2 1
        log.info("A1 getPhase:{}, getRegisteredParties:{}, getArrivedParties:{}",
                phaser.getPhase(), phaser.getRegisteredParties(), phaser.getArrivedParties());
        phaser.arrive();
        // 1 2 0
        log.info("A1 getPhase:{}, getRegisteredParties:{}, getArrivedParties:{}",
                phaser.getPhase(), phaser.getRegisteredParties(), phaser.getArrivedParties());

        new Thread(phaser::arriveAndAwaitAdvance).start();
        Thread.sleep(3000);

        phaser.arrive();
        // 2 2 0
        log.info("A1 getPhase:{}, getRegisteredParties:{}, getArrivedParties:{}",
                phaser.getPhase(), phaser.getRegisteredParties(), phaser.getArrivedParties());
    }
}
