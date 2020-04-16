package chapter05.example02;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhukaishengy
 * @Date: 2020/3/26 15:40
 * @Description:
 */
@Data
@Accessors(chain = true)
public class Person {

    private String name;
    private Integer age;
}
