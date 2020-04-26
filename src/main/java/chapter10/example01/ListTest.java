package chapter10.example01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/21 18:44
 * @Description:
 */
@Slf4j
public class ListTest {

    private List<Integer> list = new ArrayList<>();
    private Vector<Integer> vector = new Vector<>();

    @AllArgsConstructor
    @Data
    class MyCallable implements Callable<String> {

        private Integer begin;
        private Integer end;
        private Iterable iterable;

        @Override
        public String call() throws InterruptedException {
            for (int i = begin; i < end; i++) {
                if (iterable instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) this.iterable;
                    arrayList.add(i);
                }
                if (iterable instanceof Vector) {
                    Vector vector = (Vector) this.iterable;
                    vector.add(i);
                }
                Thread.sleep(100);
            }
            return "success";
        }
    }

    /**
     * ArrayList线程不安全
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test1() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 20, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100));
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

//        completionService.submit(new MyCallable(0, 50, list));
//        completionService.submit(new MyCallable(50, 100, list));

        completionService.submit(new MyCallable(0, 50, vector));
        completionService.submit(new MyCallable(50, 100, vector));

        for (int i = 0; i < 2; i++) {
            String result = completionService.take().get();
            log.info("result:{}", result);
        }
        for (int i = 0; i < 100; i++) {
//            log.info("item:{}", list.get(i));
            log.info("item:{}", vector.get(i));
        }
    }

}
