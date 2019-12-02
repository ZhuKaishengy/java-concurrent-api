package chapter01.example10;

import chapter01.common.Unsafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 20:00
 * @Description: 多进路-多处理-多出路实验
 */
@Slf4j
public class Example01 {

    private static Semaphore semaphore = new Semaphore(3);

    @Unsafe
    public void service() {
        try {
            semaphore.acquire();
            log.info("线程{}准备", Thread.currentThread().getName());
            log.info("线程{}执行", Thread.currentThread().getName());
            for (int i = 0; i < 5; i++) {
                log.info("线程{}, 执行第：{}部分", Thread.currentThread().getName(), i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }


    public static void main(String[] args) {

        Example01 instance = new Example01();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(instance::service);
            thread.start();
        }
    }
}
