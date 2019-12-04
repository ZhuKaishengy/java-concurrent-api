package chapter02.example10;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 14:14
 * @Description:
 * 从运行结果来看，全部线程都进入了catch语句块，其中thread1线程进入了Interrupted Exception的catch语句块，
 * 其他3个线程进入了BrokenBarrierException的catch语句块。
 * 类CyclicBarrier对于线程的中断interrupte处理会使用全有或者全无的破坏模型（breakage model），
 * 意思是如果有一个线程由于中断或者超时提前离开了屏障点，其他所有在屏障点等待的线程也会抛出BrokenBarrierException或者InterruptedException异常，
 * 并且离开屏障点。
 */
@Slf4j
public class MyService {

    private CyclicBarrier cyclicBarrier;

    public MyService(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            Thread.sleep((int)(Math.random() * 1000));
            log.info("name :{} first phrase begin... count :{}", threadName, cyclicBarrier.getNumberWaiting());
            if (Objects.equals(threadName, "thread1")) {
                // 1. 模拟异常
//                throw new RuntimeException("error occurs");
                // 2. 模拟线程中断
                Thread.currentThread().interrupt();
            }
            // 注意barrier的位置，在此处屏障点被破坏
            cyclicBarrier.await();
            log.info("name :{} first phrase end... count :{}", threadName, cyclicBarrier.getNumberWaiting());
            Thread.sleep(1000);
            log.info("name :{} second phrase begin... count :{}", threadName, cyclicBarrier.getNumberWaiting());
            cyclicBarrier.await();
            log.info("name :{} second phrase end... count :{}", threadName, cyclicBarrier.getNumberWaiting());
        } catch (InterruptedException e) {
            log.error("threadName:{},isBroken:{},error:{}", threadName, cyclicBarrier.isBroken(),e);
        } catch (BrokenBarrierException e) {
            log.error("threadName:{},isBroken:{},error:{}", threadName, cyclicBarrier.isBroken(),e);
        }

    }
}
