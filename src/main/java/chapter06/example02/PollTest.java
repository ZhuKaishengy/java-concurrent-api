package chapter06.example02;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Time;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 11:25
 * @Description:
 */
@Slf4j
public class PollTest {


    /**
     * 方法poll()的作用是获取并移除表示下一个已完成任务的Future，如果不存在这样的任务，则返回null，方法poll()无阻塞的效果。
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test1() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        int jobCount = 5;
        for (int i = jobCount; i > 1; i--) {
            completionService.submit(new PollCallable(i));
        }
        // 停顿2s，让执行时间最短的任务执行完
        Thread.sleep(2000);

        for (int i = 0; i < 4; i++) {
            Future<String> future = completionService.poll();
            if (Objects.isNull(future)) {
                log.info("not found");
                continue;
            }
            String result = future.get();
            log.info(result);
        }
    }

    /**
     * 方法Future<V> poll(long timeout, TimeUnit unit)的作用是等待指定的timeout时间，
     * 在timeout时间之内获取到值时立即向下继续执行，如果超时也立即向下执行。
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test2() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        int jobCount = 5;
        for (int i = jobCount; i > 1; i--) {
            completionService.submit(new PollCallable(i));
        }
        for (int i = 0; i < 4; i++) {
            Future<String> future = completionService.poll(1, TimeUnit.SECONDS);
            if (Objects.isNull(future)) {
                log.info("not found");
                continue;
            }
            String result = future.get();
            log.info(result);
        }
    }

}
