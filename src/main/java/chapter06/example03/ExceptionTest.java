package chapter06.example03;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 11:25
 * @Description:
 */
@Slf4j
public class ExceptionTest {


    /**
     * 测试异步出现异常时在哪儿抛出
     */
    @Test
    public void test1() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        int jobCount = 5;
        for (int i = jobCount; i > 1; i--) {
            completionService.submit(new ExceptionCallable(i));
        }
        for (int i = 0; i < 4; i++) {
            Future<String> future = null;
            try {
                future = completionService.take();
            } catch (InterruptedException e) {
                log.error("take() InterruptedException error...");
            }
            String result = null;
            try {
                assert future != null;
                // 在调用get()时抛出异常，出现异常后，主线程可以继续执行
                result = future.get();
            } catch (InterruptedException e) {
                log.error("get() InterruptedException error...");
            } catch (ExecutionException e) {
                log.error("get() ExecutionException error...");
            }
            log.info(result);
        }
    }
}
