package chapter01.example10;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 20:00
 * @Description: 多进路-单处理-多出路实验
 */
@Slf4j
public class Example02 {

    private static Semaphore semaphore = new Semaphore(3);
    /**
     * 在代码中加入了ReentrantLock对象，保证了同步性。相当于semaphore设置为1
     */
    private ReentrantLock lock = new ReentrantLock();

    public void service() {
        try {
            semaphore.acquire();
            lock.lock();
            log.info("线程{}准备", Thread.currentThread().getName());
            log.info("线程{}执行", Thread.currentThread().getName());
            for (int i = 0; i < 5; i++) {
                log.info("线程{}, 执行第：{}部分", Thread.currentThread().getName(), i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            semaphore.release();
        }
    }

    public static void main(String[] args) {

        Example02 instance = new Example02();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(instance::service);
            thread.start();
        }
    }
}
