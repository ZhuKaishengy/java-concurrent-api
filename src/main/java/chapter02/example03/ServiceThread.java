package chapter02.example03;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 11:31
 * @Description:
 */
public class ServiceThread extends Thread {

    private Service service;

    public ServiceThread(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.doPrepare();
    }
}
