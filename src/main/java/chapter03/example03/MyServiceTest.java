package chapter03.example03;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 16:57
 * @Description: 类Phaser的getPhase()和onAdvance()方法测试
 */
public class MyServiceTest {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(2){
            // 返回true不等待了，Phaser呈无效/销毁的状态，到达屏障5次后屏障失效
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase == 5;
            }
        };
        MyService myService = new MyService(phaser);
        Thread thread1 = new Thread(myService::run);
        thread1.setName("th1");
        Thread thread2 = new Thread(myService::run);
        thread2.setName("th2");
        thread1.start();
        thread2.start();
    }
}
