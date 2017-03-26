package qlw.util.annotation;

/**
 * Created by qlw on 2017/1/14 0014.
 *
 * @Date 2017/1/14 0014 19:19
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BatisRepository {
}
