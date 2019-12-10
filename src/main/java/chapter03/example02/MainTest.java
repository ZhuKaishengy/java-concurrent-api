package chapter03.example02;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 15:31
 * @Description: 类Phaser的arriveAndAwaitAdvance()方法测试1
 */
public class MainTest {

    private static final Integer PARTIES = 3;

    private static Phaser phaser = new Phaser(PARTIES);

    public static void main(String[] args) throws InterruptedException {
        MyService service = new MyService(phaser);
        Thread thread1 = new RunThread(service);
        thread1.setName("thread1");
        thread1.start();
        Thread.sleep(2000);

        Thread thread2 = new RunThread(service);
        thread2.setName("thread2");
        thread2.start();
        Thread.sleep(2000);

        Thread thread3 = new DeregistThread(service);
        thread3.setName("thread3");
        thread3.start();
    }
}
