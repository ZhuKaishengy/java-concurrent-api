package chapter01.example13;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 13:26
 * @Description: 主测试类
 */
@Slf4j
public class ThreadAMain {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ThreadA threadA = new ThreadA(exchanger);
        threadA.start();
        log.info("main end ...");
    }
}
