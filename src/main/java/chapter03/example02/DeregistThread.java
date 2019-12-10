package chapter03.example02;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/4 15:36
 * @Description:
 */
public class DeregistThread extends Thread{

    private MyService myService;

    public DeregistThread(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        myService.deRegist();
    }
}
