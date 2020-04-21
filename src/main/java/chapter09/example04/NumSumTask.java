package chapter09.example04;

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
public class NumSumTask extends RecursiveTask<Integer> {

    private int begin;
    private int end;

    @Override
    protected Integer compute() {
        if (end - begin > 1) {
            int middle = (begin + end) >> 1;
            NumSumTask leftTask = new NumSumTask(begin, middle);
            NumSumTask rightTask = new NumSumTask(middle + 1, end);
            invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        } else if (end - begin == 1) {
            return begin + end;
        } else if (end - begin >= 0){
            return begin;
        } else {
            return null;
        }
    }
}
