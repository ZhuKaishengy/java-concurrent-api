package chapter02.example01;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/2 13:03
 * @Description:
 */
public class MyThread extends Thread {

    private CountDownService countDownService;

    public MyThread(CountDownService countDownService) {
        this.countDownService = countDownService;
    }

    @Override
    public void run() {
        countDownService.awaitLatch();
    }
}
