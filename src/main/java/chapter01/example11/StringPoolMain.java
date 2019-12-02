package chapter01.example11;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/21 17:37
 * @Description: 主测试程序
 */
public class StringPoolMain {

    private static final StringPool stringPool = new StringPool();

    public static void main(String[] args) {

        int threadsCount = 10;
        MyThread[] threads = new MyThread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new MyThread(stringPool);
        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
        }
    }
}
