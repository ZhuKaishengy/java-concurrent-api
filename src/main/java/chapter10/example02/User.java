package chapter10.example02;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhukaishengy
 * @Date: 2020/4/26 11:29
 * @Description:
 */
@Data
@Accessors(chain = true)
public class User implements Comparable<User>{

    private Integer id;
    private String name;
    private Integer age;

    @Override
    public int compareTo(User u) {
        return Integer.compare(this.id, u.getId());
    }
}
