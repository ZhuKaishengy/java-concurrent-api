package chapter09.example06;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/21 17:08
 * @Description:
 *
 * 提供了若干个方法来监视任务池的状态：
 * ❑ 方法getParallelism()：获得并行的数量，与CPU的内核数有关；
 * ❑ 方法getPoolSize()：获得任务池的大小；
 * ❑ 方法getQueuedSubmissionCount()：取得已经提交但尚未被执行的任务数量；
 * ❑ 方法hasQueuedSubmissions()：判断队列中是否有未执行的任务；
 * ❑ 方法getActiveThreadCount()：获得活动的线程个数；
 * ❑ 方法getQueuedTaskCount()：获得任务的总个数；
 * ❑ 方法getStealCount()：获得偷窃的任务个数；
 * ❑ 方法getRunningThreadCount()：获得正在运行并且不在阻塞状态下的线程个数；
 * ❑ 方法isQuiescent()：判断任务池是否是静止未执行任务的状态。
 */
@Slf4j
public class PoolStatTest {

    @Data
    @AllArgsConstructor
    class MyRecursiveTask extends RecursiveTask<String> {

        private int count;

        @Override
        protected String compute() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return String.valueOf(count);
        }
    }

    @Test
    public void test1() {
        ForkJoinPool pool = new ForkJoinPool();
        for (int i = 0; i < 10; i++) {
            pool.submit(new MyRecursiveTask(i));
        }
        // 获得并行的数量，与CPU的内核数有关；
        int parallelism = pool.getParallelism();
        // 获得任务池的大小；
        int poolSize = pool.getPoolSize();
        // 取得已经提交但尚未被执行的任务数量；
        int queuedSubmissionCount = pool.getQueuedSubmissionCount();
        // 获得活动的线程个数；
        int activeThreadCount = pool.getActiveThreadCount();
        // 获得任务的总个数；
        long queuedTaskCount = pool.getQueuedTaskCount();
        // 获得偷窃的任务个数；
        long stealCount = pool.getStealCount();
        // 获得正在运行并且不在阻塞状态下的线程个数；
        int runningThreadCount = pool.getRunningThreadCount();

        log.info("parallelism:{}, poolSize:{}", parallelism, poolSize);
        log.info("queuedSubmissionCount:{}, activeThreadCount:{}, queuedTaskCount:{}", queuedSubmissionCount, activeThreadCount, queuedTaskCount);
        log.info("stealCount:{}, runningThreadCount:{}", stealCount, runningThreadCount);
    }
}
