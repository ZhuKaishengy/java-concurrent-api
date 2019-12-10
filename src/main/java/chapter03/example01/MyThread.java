package chapter03.example01;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 15:36
 * @Description:
 */
public class MyThread extends Thread{

    private MyService myService;

    public MyThread(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        myService.run();
    }
}
