package chapter01.example12;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 11:33
 * @Description: producer thread
 */
public class ProducerThread extends Thread{

    private DataService dataService;
    private Object data;

    public ProducerThread(DataService dataService, Object data) {
        this.dataService = dataService;
        this.data = data;
    }

    @Override
    public void run() {
        dataService.produce(data);
    }
}
