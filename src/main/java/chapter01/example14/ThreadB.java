package chapter01.example14;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 13:23
 * @Description: 自定义线程
 */
@Slf4j
public class ThreadB extends Thread {

    private Exchanger<String> exchanger;

    public ThreadB(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            log.info("threadB exchange start ...");
            // 此方法被调用后等待其他线程来取得数据，如果没有其他线程取得数据，则一直阻塞等待
            String result = exchanger.exchange("threadB data");
            log.info("threadB exchange {} ...end", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
