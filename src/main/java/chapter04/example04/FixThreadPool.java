package chapter04.example04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:46
 * @Description:
 */
@Slf4j
public class FixThreadPool {

    public static void main(String[] args) {

        // 有界线程池，核心线程数和最大线程数都为2
        ExecutorService executorService = Executors.newFixedThreadPool(2, r -> {
            Thread thread = new Thread(r);
            thread.setName("th-" + (int)(Math.random() * 1000));
            return thread;
        });

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> log.info("thread:{} run ...", Thread.currentThread().getName()));
        }
    }
}
