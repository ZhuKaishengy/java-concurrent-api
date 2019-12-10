package chapter03.example07;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 10:18
 * @Description:
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(2);
        Thread thread = new Thread(() -> {
            try {
                log.info("waiting...");
                phaser.awaitAdvanceInterruptibly(0);
                log.info("done...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("thread:{} now interrupted", Thread.currentThread().getName());
            }
        });
        thread.setName("threadA");
        thread.start();
        // awaitAdvanceInterruptibly 使用类似awaitAdvance，达到phase时等待，否则向下执行
        phaser.arrive();
        phaser.arrive();
        Thread.sleep(3000);
        // awaitAdvanceInterruptibly 方法可以被中断
        thread.interrupt();
    }
}
