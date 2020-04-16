package chapter05.example02;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 15:39
 * @Description:
 */
@Slf4j
public class SubmitTest {

    /**
     * runnable 和result 没啥用
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Person person = new Person();

        Future<Person> future = executorService.submit(new PersonRunnable(), person);
        log.info("person:{}",person);
        Person result = future.get();
        log.info("result:{}", result);
    }
}
