package chapter09.example05;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/21 14:17
 * @Description:
 */
@Slf4j
public class ForkJoinPoolMainTest {

    /**
     * 在ForkJoinPool.java类中的execute()方法是以异步的方式执行任务。
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(new RecursiveAction() {
            @Override
            protected void compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{} run...", Thread.currentThread().getName());
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    /**
     * 方法public void execute(Runnable task)的使用
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread name :{} run...", Thread.currentThread().getName());
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    /**
     * 虽然public void execute(ForkJoinTask<? > task)方法无返回值，但还是可以通过RecursiveTask对象处理返回值
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException, ExecutionException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RecursiveTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{} run...", Thread.currentThread().getName());
                return "hahaha";
            }
        };
        forkJoinPool.execute(task);
        // 通过RecursiveTask对象处理返回值
        String result = task.get();
        log.info("result:{}", result);
    }

    /**
     * 方法`public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task)`的使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test4() throws ExecutionException, InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        RecursiveTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{} run...", Thread.currentThread().getName());
                return "hahaha";
            }
        };
        ForkJoinTask<String> future = forkJoinPool.submit(task);
        String result = future.get();
        log.info("result:{}", result);
    }

    /**
     * 方法public ForkJoinTask<? > submit(Runnable task)的使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test5() throws ExecutionException, InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<?> future = forkJoinPool.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread name :{} run...", Thread.currentThread().getName());
        });
        Object result = future.get();
        log.info("result:{}", result);
    }

    /**
     * 方法`public <T> ForkJoinTask<T> submit(Callable<T> task)`的使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test6() throws ExecutionException, InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> future = forkJoinPool.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread name :{} run...", Thread.currentThread().getName());
            return "xixixi";
        });
        String result = future.get();
        log.info("result:{}", result);
    }

    @AllArgsConstructor
    @Data
    class MyRunnable implements Runnable {

        private String field;

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runnable run ...");
            field = "haha";
        }
    }

    /**
     * 方法`public <T> ForkJoinTask<T> submit(Runnable task, T result)`的使用
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test7() throws InterruptedException, ExecutionException {

        String result = "xixi";
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<?> future = forkJoinPool.submit(new MyRunnable(result), result);
        Object obj = future.get();
        log.info("result:{}, obj:{}", result, obj);
    }

    @Data
    @AllArgsConstructor
    class MyCallable implements Callable<Integer> {
        private int count;

        @Override
        public Integer call() throws Exception {
            try {
                Thread.sleep(count * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread :{} run...", Thread.currentThread().getName());
            return count;
        }
    }

    /**
     * 方法`public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)`的使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test8() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Callable<Integer>> taskList = new ArrayList<>(5);
        for (int i = 5; i > 0; i--) {
            taskList.add(new MyCallable(i));
        }
        List<Future<Integer>> futures = forkJoinPool.invokeAll(taskList);

        for (Future<Integer> future : futures) {
            Integer result = future.get();
            log.info("main:{}", result);
        }
    }

    @Test
    public void test9() throws InterruptedException, ExecutionException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> future = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{}", Thread.currentThread().getName());
                return "haha";
            }
        });
        // shutdown() 正在执行的任务不会被中断
        forkJoinPool.shutdown();
        Thread.sleep(8000);
        String result = future.get();
        log.info("result:{}", result);
        // 不能再提交新的任务了
        forkJoinPool.submit(() -> "haha");
    }

    @Test
    public void test10() throws InterruptedException, ExecutionException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> future = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{}", Thread.currentThread().getName());
                return "haha";
            }
        });
        // shutdownNow() 正在执行的任务不会被取消
        forkJoinPool.shutdownNow();
        Thread.sleep(8000);
        String result = future.get();
        log.info("result:{}", result);
    }

    @Test
    public void test11() throws InterruptedException, ExecutionException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> future = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{}", Thread.currentThread().getName());
                return "haha";
            }
        });
        log.info("isTerminated:{}", forkJoinPool.isTerminated());
        log.info("isShutdown:{}", forkJoinPool.isShutdown());
        forkJoinPool.shutdown();
        log.info("isTerminating:{}", forkJoinPool.isTerminating());
        Thread.sleep(8000);
        log.info("isTerminated:{}", forkJoinPool.isTerminated());
        log.info("isShutdown:{}", forkJoinPool.isShutdown());
        String result = future.get();
        log.info("result:{}", result);
    }

    @Test
    public void test12() throws InterruptedException, ExecutionException {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> future = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{}", Thread.currentThread().getName());
                return "haha";
            }
        });
        log.info("isTerminated:{}", forkJoinPool.awaitTermination(6, TimeUnit.SECONDS));
        String result = future.get();
        log.info("result:{}", result);
        forkJoinPool.shutdownNow();
        log.info("isTerminated:{}", forkJoinPool.awaitTermination(1, TimeUnit.SECONDS));
    }

    /**
     * invoke() 线程阻塞
     */
    @Test
    public void test13() {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        String result = forkJoinPool.invoke(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread name :{}", Thread.currentThread().getName());
                return "haha";
            }
        });
        log.info("result:{}", result);
    }
}
