package chapter02.example03;

/**
 * @Author: zhukaishengy
 * @Date: 2019/12/3 11:32
 * @Description:
 */
public class ServiceMain {

    public static void main(String[] args) {
        ServiceThread[] threads = new ServiceThread[10];
        Service service = new Service();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ServiceThread(service);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        service.doRun();
    }
}
