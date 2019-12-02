package chapter01.example11;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/21 17:06
 * @Description: 自定义线程
 */
@Slf4j
public class MyThread extends Thread{

    private StringPool pool;

    public MyThread(StringPool pool) {
        super();
        this.pool = pool;
    }

    @Override
    public void run() {
        // 每个线程一直尝试获取字符串池中的数据
        int loopCount = 5;
        for (int i = 0; i < loopCount; i++) {
            String item = pool.get();
            String threadName = Thread.currentThread().getName();
            log.info("当前线程:{}，获取到了数据{}。。。", threadName, item);
            pool.add(item);
            log.info("当前线程:{}，返回了数据{}。。。", threadName, item);
        }
    }
}
