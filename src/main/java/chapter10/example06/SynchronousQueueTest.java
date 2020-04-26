package chapter10.example06;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:48
 * @Description:
 */
@Slf4j
public class SynchronousQueueTest {

    @Test
    public void test1() throws InterruptedException {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        Thread thread1 = new Thread(() -> {
            try {
                synchronousQueue.put(1);
                log.info("put success...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        Thread.sleep(5000);
        Thread thread2 = new Thread(() -> {
            try {
                Integer result = synchronousQueue.take();
                log.info("take success:{}...", result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        Thread.sleep(5000);
    }
}
