package chapter01.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/21 12:21
 * @Description: 初识semaphore
 */
@Slf4j
public class SemaphoreHelloWorld {

    /**
     * 如果多次调用Semaphore类的release()或release(int)方法时，还可以动态增加permits的个数。
     * 此实验说明构造参数new Semaphore(5)；中的5并不是最终的许可数量，仅仅是初始的状态值
     * @param args
     */
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        try {
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            log.info("currrent permits count :{}", semaphore.availablePermits());
            semaphore.release(6);
            log.info("currrent permits count :{}", semaphore.availablePermits());
        } catch (InterruptedException e) {
            log.error("error occurs:{}", e);
        }
    }
}
