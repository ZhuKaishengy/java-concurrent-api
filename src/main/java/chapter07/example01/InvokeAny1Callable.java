package chapter07.example01;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 13:57
 * @Description:
 */
@AllArgsConstructor
@Slf4j
public class InvokeAny1Callable implements Callable<String> {

    private CountDownLatch countDownLatch;

    private int count;

    @Override
    public String call() {
        try {
            Thread.sleep(count * 1000);
            log.info("callable {} run...", count);
            return "haha-" + count;
        } catch (InterruptedException e) {
            log.error("call {} InterruptedException...", count);
            // 异步线程被中断后，抛出的异常不会在主线程被捕获到。
//            throw new RuntimeException("call " + count +" InterruptedException...");
        } finally {
            countDownLatch.countDown();
        }
        return null;
    }
}
