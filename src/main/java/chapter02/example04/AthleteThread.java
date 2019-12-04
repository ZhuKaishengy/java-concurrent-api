package chapter02.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 12:52
 * @Description: 运动员
 */
@Slf4j
public class AthleteThread extends Thread{

    CountDownLatch comingLatch;
    CountDownLatch beginLatch;
    CountDownLatch downLatch;
    CountDownLatch gameOverLatch;

    public AthleteThread(CountDownLatch comingLatch, CountDownLatch beginLatch,
                         CountDownLatch downLatch, CountDownLatch gameOverLatch) {
        this.comingLatch = comingLatch;
        this.beginLatch = beginLatch;
        this.downLatch = downLatch;
        this.gameOverLatch = gameOverLatch;
    }

    @Override
    public void run() {
        log.info("运动员：{}, 正在赶往战场。。。", Thread.currentThread().getName());
        try {
            Thread.sleep((int)(Math.random() * 1000));
            log.info("运动员：{}, 到达战场。。。", Thread.currentThread().getName());
            comingLatch.countDown();
            log.info("运动员：{}, 等待比赛开始。。。", Thread.currentThread().getName());
            beginLatch.await();
            log.info("运动员：{}, 开始比赛。。。", Thread.currentThread().getName());
            Thread.sleep((int)(Math.random() * 1000));
            log.info("运动员：{}, 完成个人赛程。。。", Thread.currentThread().getName());
            downLatch.countDown();
            log.info("运动员：{}, 等待比赛完成。。。", Thread.currentThread().getName());
            gameOverLatch.await();
            log.info("运动员：{}, 收到比赛完成消息。。。", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
