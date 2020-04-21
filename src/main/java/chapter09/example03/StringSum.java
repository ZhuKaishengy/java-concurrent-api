package chapter09.example03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/20 13:24
 * @Description:
 */
@Slf4j
public class StringSum {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task = forkJoinPool.submit(new StringSumTask(1, 20));
        String result = task.join();
        log.info("result:{}", result);
    }
}
