package chapter06.example02;

import java.util.concurrent.Callable;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 10:48
 * @Description:
 */
public class PollCallable implements Callable<String> {

    private int count;

    public PollCallable(int count) {
        this.count = count;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(count * 1000);
        return "haha" + count;
    }
}
