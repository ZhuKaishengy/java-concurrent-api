package chapter09.example03;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/20 13:26
 * @Description:
 */
@Data
@AllArgsConstructor
public class StringSumTask extends RecursiveTask<String> {

    private int begin;
    private int end;

    @Override
    protected String compute() {
        if (end - begin > 1) {
            int middle = (begin + end) >> 1;
            StringSumTask leftTask = new StringSumTask(begin, middle);
            StringSumTask rightTask = new StringSumTask(middle + 1, end);
            invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        } else if (end - begin == 1) {
            return String.valueOf(begin) + String.valueOf(end);
        } else if (end - begin >= 0){
            return String.valueOf(begin);
        } else {
            return null;
        }
    }
}
