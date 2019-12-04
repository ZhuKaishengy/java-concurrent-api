package common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @Author: zhukaishengy
 * @Date: 2019/11/19 11:35
 * @Description: 用于标示一个类或方法不是线程安全的
 */
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Unsafe {
}
