package chapter05.example02;

import lombok.Data;

import java.util.Objects;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 15:41
 * @Description:
 */
@Data
public class PersonRunnable implements Runnable {

    private Person person;

    @Override
    public void run() {
        if (Objects.isNull(person)) {
            person = new Person().setName("zks").setAge(25);
        } else {
            person.setName("zks").setAge(25);
        }
    }
}
