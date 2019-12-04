package chapter02.example05;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 13:22
 * @Description:
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        try {
            log.info("begin...");
            latch.await(3, TimeUnit.SECONDS);
            log.info("continue...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
