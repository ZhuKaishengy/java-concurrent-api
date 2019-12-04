package chapter02.example09;

import java.util.concurrent.BrokenBarrierException;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 13:31
 * @Description:
 */
public class MyThread extends Thread {

    private MyService myService;

    public MyThread(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        try {
            this.myService.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
