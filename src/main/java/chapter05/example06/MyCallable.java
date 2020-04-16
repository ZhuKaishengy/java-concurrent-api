package chapter05.example06;

import java.util.concurrent.Callable;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/27 10:14
 * @Description:
 */
public class MyCallable implements Callable<String> {

    private int count;

    public MyCallable(int count) {
        this.count = count;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        Thread.sleep(count * 1000);
        return "调用第"+ count +"次";
    }
}
