package chapter03.example06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/7 13:30
 * @Description:
 *  如果传入参数phase值和当前getPhase()方法返回值一样，则在屏障处等待，否则继续向下面运行，有些类似于旁观者的作用，
 *  当观察的条件满足了就等待（旁观），如果条件不满足，则程序向下继续运行。
 */
@Slf4j
public class MainTest {
    public static void main(String[] args) throws InterruptedException {

        Phaser phaser = new Phaser(2){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                log.info("phaser achieve barrier");
                return super.onAdvance(phase, registeredParties);
            }
        };

        Thread thread1 = new Thread(() -> {
            log.info(String.valueOf(phaser.getPhase()));
            phaser.awaitAdvance(0);
            log.info("thread1 after await ...");
        });

        Thread thread2 = new Thread(() -> {
            log.info(String.valueOf(phaser.getPhase()));
            phaser.awaitAdvance(0);
            log.info("thread2 after await ...");
        });

        thread1.start();
        thread2.start();
        // awaitAdvance 方法不可被中断
        thread1.interrupt();
        Thread.sleep(5000);

        while (phaser.getPhase() < 1) {
            log.info("++");
            phaser.arrive();
        }

    }
}
