package chapter01.example12;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 11:37
 * @Description: DataService 测试主程序
 */
public class DataServiceMain {

    private static final DataService DATASERVICE = new DataService();

    public static void main(String[] args) throws InterruptedException {

        Thread[] producers = new Thread[30];
        Thread[] consumers = new Thread[30];
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new ProducerThread(DATASERVICE, "数据-" + i);
        }
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new ConsumerThread(DATASERVICE);
        }
        Thread.sleep(1000);
        for (int i = 0; i < 30; i++) {
            producers[i].start();
            consumers[i].start();
        }
    }
}
