package chapter06.example03;

import java.util.concurrent.Callable;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 10:48
 * @Description:
 */
public class ExceptionCallable implements Callable<String> {

    private int count;

    public ExceptionCallable(int count) {
        this.count = count;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(count * 1000);
        if (count == 4) {
            throw new RuntimeException("error occur");
        }
        return "haha" + count;
    }
}
