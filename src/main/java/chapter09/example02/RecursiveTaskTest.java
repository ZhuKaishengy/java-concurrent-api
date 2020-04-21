package chapter09.example02;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/20 13:01
 * @Description:
 *
 */
@Slf4j
public class RecursiveTaskTest {

//    使用RecursiveTask取得返回值与join()和get()方法的区别，#get可以在主线程中捕获异常    //
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return "hh";
            }
        });
        String result = task.get();
        log.info("result:{}", result);
    }

    @Test
    public void test2() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return "hh";
            }
        });
        String result = task.join();
        log.info("result:{}", result);
    }

    @Test
    public void test3() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                throw new RuntimeException("haha");
            }
        });
        String result = null;
        try {
            result = task.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("error occor :{}", e.toString());
        }
        log.info("result:{}", result);
    }

    @Test
    public void test4() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                throw new RuntimeException("haha");
            }
        });
        String result = task.join();
        log.info("result:{}", result);
    }

//    =================================================================================    //

    /**
     * #join会线程阻塞
     */
    @Test
    public void test5() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> task1 = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hh" + Thread.currentThread().getName();
            }
        });
        ForkJoinTask<String> task2 = forkJoinPool.submit(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return "hh" + Thread.currentThread().getName();
            }
        });
        String result1 = task1.join();
        log.info("result1:{}", result1);
        String result2 = task2.join();
        log.info("result2:{}", result2);
    }
}
