package chapter02.example08;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 11:05
 * @Description: 验证屏障重置性
 */
@Slf4j
public class CyclicBarrierTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        log.info("touch barrier");
    });

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        log.info("current awaiting {}", cyclicBarrier.getNumberWaiting());
        Thread thread2 = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        log.info("current awaiting {}", cyclicBarrier.getNumberWaiting());
        Thread thread3 = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread3.start();
        log.info("current awaiting {}", cyclicBarrier.getNumberWaiting());
        Thread thread4 = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread4.start();
        log.info("current awaiting {}", cyclicBarrier.getNumberWaiting());
        Thread thread5 = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread5.start();
        log.info("current awaiting {}", cyclicBarrier.getNumberWaiting());
    }
}
