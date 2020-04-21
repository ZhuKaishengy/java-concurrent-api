package chapter09.example01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/20 11:17
 * @Description:
 */
@Data
@AllArgsConstructor
@Slf4j
public class MyRecursiveAction extends RecursiveAction {

    private int begin;
    private int end;

    @Override
    protected void compute() {
        if (end - begin > 1) {
            int middle = (begin + end) >> 1;
            MyRecursiveAction leftAction = new MyRecursiveAction(begin, middle);
            MyRecursiveAction rightAction = new MyRecursiveAction(middle, end);
            invokeAll(leftAction, rightAction);
        } else {
            log.info("thread name:{}, begin:{}, end:{}", Thread.currentThread().getName(), begin, end);
        }
    }
}
