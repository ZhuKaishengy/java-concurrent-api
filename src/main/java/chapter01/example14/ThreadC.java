package chapter01.example14;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 13:23
 * @Description: 自定义线程
 */
@Slf4j
public class ThreadC extends Thread {

    private Exchanger<String> exchanger;

    public ThreadC(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            log.info("threadC exchange start ...");
            // 当调用exchange(V x, long timeout, TimeUnit unit)方法后在指定的时间内没有其他线程获取数据，则出现超时异常。
            String result = exchanger.exchange("threadC data", 1, TimeUnit.SECONDS);
            log.info("threadC exchange {} ...end", result);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
