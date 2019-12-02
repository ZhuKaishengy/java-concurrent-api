package chapter01.example11;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/21 16:39
 * @Description: 字符串池，同时有若干个线程可以访问池中的数据，但同时只有一个线程可以取得数据，使用完毕后再放回池中。
 */
@Slf4j
public class StringPool {

    private int maxPoolSize = 3;
    private int permits = 5;

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private Semaphore semaphore = new Semaphore(permits);
    private List<String> strs = new ArrayList<>(maxPoolSize);

    public StringPool() {
        // 初始化
        for (int i = 0; i < maxPoolSize; i++) {
            strs.add("str-" + i);
        }
    }

    /**
     * 只有一个线程可以取得数据
     * @return
     */
    public String get() {
        String result = "";
        try {
            // 消耗一个permit，加锁，池中没有数据，等待
            semaphore.acquire();
            lock.lock();

            if (strs.size() == 0) {
                log.info("字符串池中没有数据，waiting...");
                condition.await();
            } else {
                result = strs.remove(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
        return result;
    }

    /**
     * 使用后放回池中
     * @param str
     */
    public void add(String str) {
        lock.lock();
        strs.add(str);
        condition.signalAll();
        lock.unlock();
        semaphore.release();
    }

}
