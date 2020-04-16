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
public class InvokeAll1Callable implements Callable<String> {

    private int count;

    @Override
    public String call() throws Exception {
        try {
            Thread.sleep(count * 1000);
            log.info("id :{} run ...", count);
        } catch (Exception e) {
            log.error("call exception :{}", e.toString());
        }
        return "run-" + count;
    }
}
