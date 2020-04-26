package chapter10.example07;

import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:55
 * @Description:
 */
@Slf4j
public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<UserInfo> userInfos = new DelayQueue<>();
        log.info("begin:{}", new Date());
        userInfos.put(new UserInfo().setUsername("zks").setDelay(10).setTimeUnit(TimeUnit.SECONDS));
        log.info("put:{}", new Date());
        UserInfo result = userInfos.take();
        log.info("end:{}, result:{}", new Date(), result);
    }
}
