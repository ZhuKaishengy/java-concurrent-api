package chapter02.example01;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/2 13:05
 * @Description:
 */
public class CountDownServiceMain {

    public static void main(String[] args) throws InterruptedException {
        CountDownService countDownService = new CountDownService();
        MyThread myThread = new MyThread(countDownService);
        myThread.start();
        Thread.sleep(3000);
        countDownService.countDown();
    }
}
