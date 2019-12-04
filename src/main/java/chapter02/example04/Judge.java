package chapter02.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 13:02
 * @Description:
 */
@Slf4j
public class Judge {

    public static void main(String[] args) {

        AthleteThread[] athleteThreads = new AthleteThread[10];
        // 10人参加比赛
        CountDownLatch comingLatch = new CountDownLatch(10);
        // 裁判宣布比赛开始
        CountDownLatch beginLatch = new CountDownLatch(1);
        // 等待运动员完成比赛
        CountDownLatch downLatch = new CountDownLatch(10);
        // 裁判宣布比赛结束
        CountDownLatch gameOverLatch = new CountDownLatch(1);

        for (int i = 0; i < athleteThreads.length; i++) {
            athleteThreads[i] = new AthleteThread(comingLatch, beginLatch, downLatch, gameOverLatch);
        }
        for (int i = 0; i < athleteThreads.length; i++) {
            athleteThreads[i].start();
        }

        try {
            log.info("裁判等待运动员到来");
            comingLatch.await();
            log.info("裁判宣布比赛开始");
            beginLatch.countDown();
            log.info("裁判等待运动员完成比赛");
            downLatch.await();
            log.info("裁判宣布比赛结束");
            gameOverLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
