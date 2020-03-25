package chapter04.example01;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:07
 * @Description:
 */
@Slf4j
public class CachedThreadPoolTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {

            executorService.execute(() -> {
                try {
                    log.info("thread:{}, start...", Thread.currentThread().getName());
                    Thread.sleep(1000);
                    log.info("thread:{}, end...", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
