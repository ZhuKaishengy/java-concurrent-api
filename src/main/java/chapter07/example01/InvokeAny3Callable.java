package chapter07.example01;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 14:34
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class InvokeAny3Callable implements Callable<String> {

    private int count;

    @Override
    public String call() throws Exception {
        Thread.sleep(count * 1000);
        log.error("job:{} run error...", count);
        throw new RuntimeException("job:"+ count +" run error...");
    }
}
