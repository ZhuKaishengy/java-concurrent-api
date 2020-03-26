package chapter04.example10;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/25 17:02
 * @Description: 处理任务被拒绝执行时的行为
 */
@Slf4j
public class RejectedExecutionHandlerTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS,
                new SynchronousQueue<>(true));

        // 注意：这个拒绝策略在主线程执行
        threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            // TODO 如何清理
            Map<Runnable, AtomicInteger> map = new ConcurrentHashMap<>();

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.error("任务被拒绝了，尝试重新执行");
                AtomicInteger count = map.get(r);
                if (Objects.isNull(count)) {
                    try {
                        map.put(r, new AtomicInteger(1));
                        // 3s后重新尝试
                        Thread.sleep(3000);
                        threadPoolExecutor.execute(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else if (count.get() < 3) {
                    try {
                        count.incrementAndGet();
                        map.put(r, count);
                        // 3s后重新尝试
                        Thread.sleep(3000);
                        threadPoolExecutor.execute(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                   map.remove(r);
                   log.error("尝试3次都未成功");
                }
            }
        });
        Phaser phaser = new Phaser(3);
        for (int i = 0; i < 2; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(5000);
                    log.info("thread name:{}", Thread.currentThread().getName());
                    phaser.arrive();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 没有线程可用了，会抛出异常
        threadPoolExecutor.execute(() -> {
            log.info("thread name:{}", Thread.currentThread().getName());
            phaser.arrive();
        });

        phaser.awaitAdvance(1);
        threadPoolExecutor.shutdown();
    }
}
