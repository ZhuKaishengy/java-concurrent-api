package chapter10.example02;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 11:27
 * @Description:
 */
@Slf4j
public class MainTest {

    @Test
    public void concurrentSkipListMapTest() {
        ConcurrentSkipListMap<Integer, User> map = new ConcurrentSkipListMap<>();
        map.put(2, new User().setId(2).setName("haha2").setAge(2));
        map.put(3, new User().setId(3).setName("haha3").setAge(3));
        map.put(1, new User().setId(1).setName("haha1").setAge(1));
        Map.Entry<Integer, User> entry = map.pollFirstEntry();
        log.info("result:{}", entry.getValue());
    }

    @Test
    public void concurrentSkipListSetTest() {
        ConcurrentSkipListSet<User> set = new ConcurrentSkipListSet<>();
        User haha1 = new User().setId(1).setName("haha1").setAge(1);
        set.add(new User().setId(2).setName("haha2").setAge(2));
        set.add(new User().setId(3).setName("haha3").setAge(3));
        set.add(haha1);
        set.add(haha1);
        User user = set.pollFirst();
        log.info("result:{}", user);
    }

    @Test
    public void concurrentLinkedQueueTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Integer i1 = queue.poll();
        Integer i2 = queue.poll();
        Integer i3 = queue.peek();
        Integer i4 = queue.element();
        log.info("i1:{},i2:{},i3:{},i4:{}", i1, i2, i3, i4);
    }

    @Test
    public void concurrentLinkedDequeTest() {
        ConcurrentLinkedDeque<Integer> queue = new ConcurrentLinkedDeque<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.addFirst(0);
        Integer i1 = queue.pollFirst();
        Integer i2 = queue.pollLast();
        log.info("i1:{},i2:{}", i1, i2);
    }

}
