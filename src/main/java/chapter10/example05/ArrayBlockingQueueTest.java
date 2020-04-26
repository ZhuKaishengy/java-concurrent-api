package chapter10.example05;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:30
 * @Description:
 */
@Slf4j
public class ArrayBlockingQueueTest {

    @Test
    public void test1() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        try {
            queue.put(1);
            queue.put(2);
            queue.put(3);
            log.info("size:{}", queue.size());
            queue.put(4);
            log.info("size:{}", queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        log.info("size:{}", queue.size());
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
