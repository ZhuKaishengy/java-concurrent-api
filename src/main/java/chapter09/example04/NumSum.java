package chapter09.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/20 13:24
 * @Description:
 */
@Slf4j
public class NumSum {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> task = forkJoinPool.submit(new NumSumTask(1, 20));
        Integer result = task.join();
        log.info("result:{}", result);
    }
}
