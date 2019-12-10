package chapter03.example01;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 15:31
 * @Description: 类Phaser的arriveAndAwaitAdvance()方法测试1
 */
public class MainTest {

    // 计数为3正常执行，设置为4，所有线程wait
    private static final Integer PARTIES = 4;

    private static Phaser phaser = new Phaser(PARTIES);

    public static void main(String[] args) {
        MyService service = new MyService(phaser);
        Thread thread1 = new MyThread(service);
        thread1.setName("thread1");
        thread1.start();
        Thread thread2 = new MyThread(service);
        thread2.setName("thread2");
        thread2.start();
        Thread thread3 = new MyThread(service);
        thread3.setName("thread3");
        thread3.start();
    }
}
