package chapter03.example03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 16:51
 * @Description:  类Phaser的getPhase()和onAdvance()方法测试
 */
@Slf4j
public class MyService {

    private Phaser phaser;

    public MyService(Phaser phaser) {
        this.phaser = phaser;
    }

    public void run() {
        final String THREADNAME = Thread.currentThread().getName();
        for (int i = 0; i < 10; i++) {
            log.info("name:{}, arrived barrier, number:{}", THREADNAME, i + 1);
            phaser.arriveAndAwaitAdvance();
            // 方法getPhase()获取的是已经到达第几个屏障
            log.info("name:{}, through barrier count:{}", THREADNAME, phaser.getPhase());
            if (!phaser.isTerminated()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
