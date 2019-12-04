package chapter02.example01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/2 10:35
 * @Description:
 */
@Slf4j
public class CountDownService {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void awaitLatch() {
        try {
            log.info("await begin...");
            countDownLatch.await();
            log.info("await end...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void countDown() {
        log.info("countDown begin...");
        countDownLatch.countDown();
        log.info("countDown end...");
    }
}
