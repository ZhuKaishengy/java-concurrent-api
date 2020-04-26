package chapter10.example07;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 13:56
 * @Description:
 */
@Data
@Accessors(chain = true)
public class UserInfo implements Delayed {

    private String username;
    private int delay;
    private TimeUnit timeUnit;

    @Override
    public long getDelay(TimeUnit unit) {
        return System.nanoTime() + unit.toNanos(delay);
    }

    @Override
    public int compareTo(Delayed o) {
        return 1;
    }
}
