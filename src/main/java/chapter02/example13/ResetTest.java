package chapter02.example13;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 14:50
 * @Description:
 */
@Slf4j
public class ResetTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> log.info("achieve barrier..."));

    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                try {
                    log.info("parties count:{},achieve barrier count:{}", cyclicBarrier.getParties(), cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    log.error("threadName:{},isBroken:{},error:{}", threadName, cyclicBarrier.isBroken(),e);
                } catch (BrokenBarrierException e) {
                    // 屏障被重置后，2个线程出现Broken异常。
                    log.error("threadName:{},isBroken:{},error:{}", threadName, cyclicBarrier.isBroken(),e);
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
        // 重置屏障
        cyclicBarrier.reset();
    }
}
