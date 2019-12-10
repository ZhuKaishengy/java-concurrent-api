package chapter03.example10;

import java.util.concurrent.Phaser;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/10 11:06
 * @Description:
 */
public class Run1 {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(3);
        for (int i = 0; i < 3; i++) {
            MyThread myThread = new MyThread(phaser);
            myThread.start();
        }
    }
}
