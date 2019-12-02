package chapter01.example14;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 13:26
 * @Description: 主测试类
 */
@Slf4j
public class ExchangerMain {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ThreadA threadA = new ThreadA(exchanger);
        ThreadB threadB = new ThreadB(exchanger);
        ThreadC threadC = new ThreadC(exchanger);
        threadA.start();
        threadB.start();
        threadC.start();
        log.info("main end ...");
    }
}
