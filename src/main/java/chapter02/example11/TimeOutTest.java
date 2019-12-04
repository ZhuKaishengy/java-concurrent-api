package chapter02.example11;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 14:37
 * @Description: 方法await(long timeout, TimeUnit unit)超时出现异常的测试
 */
@Slf4j
public class TimeOutTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> log.info("achieve barrier"));

    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    cyclicBarrier.await(3, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }
    }
}
