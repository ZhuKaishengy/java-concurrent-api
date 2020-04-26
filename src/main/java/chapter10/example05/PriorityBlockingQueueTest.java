package chapter10.example05;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:40
 * @Description:
 */
@Slf4j
public class PriorityBlockingQueueTest {

    /**
     * 优先级队列，支持排序
     */
    @Test
    public void test1() {
        BlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(3);
        queue.add(1);
        queue.add(2);
        Integer num1 = queue.poll();
        log.info("num1:{}", num1);
    }
}
