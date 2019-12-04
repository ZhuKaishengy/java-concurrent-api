package chapter02.example09;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 13:26
 * @Description: 用CyclicBarrier类实现阶段跑步比赛
 */
@Slf4j
public class MyService {

    private CyclicBarrier cyclicBarrier;

    public MyService(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() throws InterruptedException, BrokenBarrierException {
        String threadName = Thread.currentThread().getName();
        Thread.sleep((int)(Math.random() * 1000));
        log.info("name :{} first phrase begin...waiting count :{}", threadName, cyclicBarrier.getNumberWaiting());
        cyclicBarrier.await();
        log.info("name :{} first phrase end...waiting count :{}", threadName, cyclicBarrier.getNumberWaiting());
        Thread.sleep(1000);
        log.info("name :{} second phrase begin...waiting count :{}", threadName, cyclicBarrier.getNumberWaiting());
        cyclicBarrier.await();
        log.info("name :{} second phrase end...waiting count :{}", threadName, cyclicBarrier.getNumberWaiting());
    }
}

