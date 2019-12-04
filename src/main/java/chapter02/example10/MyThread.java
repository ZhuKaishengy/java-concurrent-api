package chapter02.example10;


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
        this.myService.run();
    }
}
