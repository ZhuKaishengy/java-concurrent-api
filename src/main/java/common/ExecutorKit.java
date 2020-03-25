package common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/11 10:56
 * @Description:
 */
public class ExecutorKit {

    /**
     * 在单元测试中使用线程池，主方法结束，调用<code>System.exit()</code>，其余线程都推出，
     * 此方法用于线程等待
     */
    public static void shutdownAndWait(ExecutorService executor) {
        executor.shutdown();
        boolean loop = false;
        while (!loop) {
            try {
                loop = !executor.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
