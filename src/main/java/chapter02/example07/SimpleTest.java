package chapter02.example07;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 19:46
 * @Description: 初步使用
 */
@Slf4j
public class SimpleTest {

    public static void main(String[] args) {

        // 设置最大为5个的parties同行者，也就是5个线程都执行了cbRef对象的await()方法后程序才可以继续向下运行，
        // 否则这些线程彼此互相等待，一直呈阻塞状态。
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> log.info("run..."));

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    log.info("{} :start...", Thread.currentThread().getName());
                    cyclicBarrier.await();
                    log.info("{} :end...", Thread.currentThread().getName());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        log.info("main thread end ...");
    }
}
