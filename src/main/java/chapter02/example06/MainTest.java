package chapter02.example06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 13:25
 * @Description:
 */
@Slf4j
public class MainTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        log.info("current count :{}", countDownLatch.getCount());
        countDownLatch.countDown();
        log.info("current count :{}", countDownLatch.getCount());
        countDownLatch.countDown();
        log.info("current count :{}", countDownLatch.getCount());
        countDownLatch.countDown();
        log.info("current count :{}", countDownLatch.getCount());
        countDownLatch.countDown();
        log.info("current count :{}", countDownLatch.getCount());
    }
}
