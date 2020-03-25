package chapter04.example03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 17:07
 * @Description:
 */
@Slf4j
public class CachedThreadPoolTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool(r -> {
            // 自定义线程工厂
            Thread thread = new Thread(r);
            thread.setName("haha" + (int)(Math.random() * 1000));
            return thread;
        });

        executorService.execute(() -> log.info("thread:{}, run...", Thread.currentThread().getName()));

    }
}
