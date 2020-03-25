package chapter04.example05;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 18:07
 * @Description:
 */
@Slf4j
public class SingleThreadExecutor {

    public static void main(String[] args) {

        // 相当于单线程
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    log.info("threadName:{},start...", Thread.currentThread().getName());
                    Thread.sleep(3000);
                    log.info("threadName:{},end...", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
