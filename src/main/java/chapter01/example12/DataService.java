package chapter01.example12;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/21 17:54
 * @Description: 本实验的目的不光是实现生产者与消费者模式，还要限制生产者与消费者的数量
 */
@Slf4j
public class DataService {

    // 生产者5个并发
    volatile private Semaphore producerSemaphore = new Semaphore(5);
    // 消费者10个并发
    volatile private Semaphore consumerSemaphore = new Semaphore(10);

    volatile private ReentrantLock lock = new ReentrantLock();
    volatile private Condition producerCondition = lock.newCondition();
    volatile private Condition consumerCondition = lock.newCondition();

    // 生产者产生的数据存储，4个数据
    private static final int STORE_INITIAL_SIZE = 4;
    volatile private List<Object> storeList = new ArrayList<>(STORE_INITIAL_SIZE);

    /**
     * 存储中是否为空
     * @return
     */
    public boolean isEmpty() {
       return storeList.isEmpty();
    }

    /**
     * 存储中是否都有值
     * @return
     */
    public boolean isFull() {
        return storeList.size() == STORE_INITIAL_SIZE && storeList.stream().allMatch(Objects::nonNull);
    }

    /**
     * 生产者生产数据
     * @param obj data
     */
    public void produce(Object obj) {
        try {
            producerSemaphore.acquire();
            lock.lock();
            while (isFull()) {
                log.info("producer:{} is waiting...", Thread.currentThread().getName());
                producerCondition.await();
            }
            storeList.add(obj);
            log.info("producer:{} produce object :{}", Thread.currentThread().getName(), obj);
            consumerCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            producerSemaphore.release();
        }
    }

    /**
     * 消费者消费数据
     * @return data
     */
    public Object consume() {
        try {
            consumerSemaphore.acquire();
            lock.lock();
            while (isEmpty()) {
                log.info("consumer:{} is waiting...", Thread.currentThread().getName());
                consumerCondition.await();
            }
            Object data = storeList.remove(0);
            log.info("consumer:{} comsume object :{}", Thread.currentThread().getName(), data);
            producerCondition.signalAll();
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            consumerSemaphore.release();
        }
        return null;
    }


}
