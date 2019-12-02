package chapter01.example12;

import lombok.Data;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/25 11:33
 * @Description: consumer thread
 */
@Data
public class ConsumerThread extends Thread{

    private DataService dataService;

    public ConsumerThread(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void run() {
        super.run();
        // 线程执行，将获取的值会写到data中
        dataService.consume();
    }
}
