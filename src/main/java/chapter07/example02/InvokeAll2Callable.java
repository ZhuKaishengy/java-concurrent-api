package chapter07.example02;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/16 16:33
 * @Description:
 */
@AllArgsConstructor
@Slf4j
public class InvokeAll2Callable implements Callable<String> {

    private int count;

    @Override
    public String call() throws Exception {
        Thread.sleep(count * 1000);
        if (count == 3){
            log.error("call error...");
            throw new RuntimeException("call error...");
        }
        log.info("id :{} run ...", count);
        return "run-" + count;
    }
}
