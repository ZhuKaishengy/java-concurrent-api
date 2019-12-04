package chapter02.example03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 11:19
 * @Description:
 */
@Slf4j
public class Service {

    private CountDownLatch latch = new CountDownLatch(1);

    public void doPrepare() {
        try {
            log.info("{},运动员准备。。。",Thread.currentThread().getName());
            latch.await();
            log.info("{},运动员开跑。。。",Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doRun() {
        log.info("枪响。。。");
        latch.countDown();
    }
}
