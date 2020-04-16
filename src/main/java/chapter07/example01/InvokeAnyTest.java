package chapter07.example01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 13:38
 * @Description: 方法invokeAny(Collection tasks)的使用与interrupt
 */
@Slf4j
public class InvokeAnyTest {

    /**
     * 无if (Thread.currentThread().isInterrupted())代码：已经获得第一个运行的结果值后，其他线程被中断。
     * 有if (Thread.currentThread().isInterrupted())代码：已经获得第一个运行的结果值后，其他线程如果使用throw new InterruptedException()代码则这些线程中断，
     * 虽然throw抛出了异常，但在main线程中并不能捕获异常。如果想捕获异常，则需要在Callable中使用try-catch显式进行捕获。
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> jobs = new ArrayList<>();

        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            jobs.add(new InvokeAny1Callable(countDownLatch, i));
        }
        String result = null;
        try {
            result = executorService.invokeAny(jobs);
        } catch (InterruptedException e) {
            log.error("InterruptedException...");
        } catch (ExecutionException e) {
            log.error("ExecutionException...");
        } catch (Exception e) {
            // 异步线程被中断后，抛出的异常不会在主线程被捕获到。
            log.error("exception occur:{}", e.getMessage());
        }
        log.info(result);
        countDownLatch.await();
    }

    /**
     * 执行快的任务抛出异常，main线程依然抓不到，会去等第一个可以执行完的任务执行完后，剩下的任务interrupt
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<String>> jobs = new ArrayList<>();

        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            jobs.add(new InvokeAny2Callable(countDownLatch, i));
        }
        String result = null;
        try {
            result = executorService.invokeAny(jobs);
        } catch (InterruptedException e) {
            log.error("InterruptedException...");
        } catch (ExecutionException e) {
            log.error("ExecutionException...");
        } catch (Exception e) {
            // 异步线程被中断后，抛出的异常不会在主线程被捕获到。
            log.error("exception occur:{}", e.getMessage());
        }
        log.info(result);
        countDownLatch.await();
    }

    /**
     * 此实验验证在全部任务都出现异常时，程序抛出ExecutionException异常。出现异常时返回最后一个异常并输出。
     */
    @Test
    public void test3() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        int count = 5;
        List<InvokeAny3Callable> callableList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            callableList.add(new InvokeAny3Callable(i));
        }
        try {
            executorService.invokeAny(callableList);
        } catch (InterruptedException | ExecutionException e) {
            log.error("ex:{}", e);
        }
    }

    /**
     * 方法invokeAny(CollectionTasks, timeout, timeUnit)超时的测试
     */
    @Test
    public void test4() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count - 1);
        List<InvokeAny1Callable> callableList = new ArrayList<>(count - 1);
        for (int i = 1; i < count; i++) {
            callableList.add(new InvokeAny1Callable(countDownLatch, i));
        }
        try {
//            String result = executorService.invokeAny(callableList, 1, TimeUnit.MICROSECONDS);
            String result = executorService.invokeAny(callableList, 2, TimeUnit.SECONDS);
            log.info("result:{}", result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("ex:{}", e);
        }
    }
}
