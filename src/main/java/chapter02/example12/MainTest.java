package chapter02.example12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 14:43
 * @Description: 方法getNumberWaiting()和getParties()
 */
@Slf4j
public class MainTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> log.info("achieve barrier..."));

    public static void main(String[] args) {
        Thread[] threads = new Thread[6];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    log.info("parties count:{},achieve barrier count:{}", cyclicBarrier.getParties(), cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
            // 多个线程启动有间隔
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
