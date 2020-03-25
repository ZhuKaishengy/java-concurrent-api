package chapter04.example02;

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

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> log.info("[first] thread:{}, execute...", Thread.currentThread().getName()));
        }
        // 间隔3s，保证上面的线程执行完了，验证下面的循环是否会复用上面的线程
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> log.info("[second] thread:{}, execute...", Thread.currentThread().getName()));
        }
    }
}
