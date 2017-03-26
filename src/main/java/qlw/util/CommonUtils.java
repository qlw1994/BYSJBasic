package qlw.util;

/**
 * Created by qlw on 2017/1/14 0014.
 *
 * @Date 2017/1/14 0014 18:12
 */
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class CommonUtils {
    public CommonUtils() {
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNull(Integer numb) {
        return null == numb || numb.intValue() == 0;
    }

    public static boolean isNull(Long numb) {
        return null == numb || numb.longValue() == 0L;
    }

    public static boolean isNull(Double numb) {
        return null == numb || numb.doubleValue() == 0.0D;
    }

    public static boolean isNull(Byte numb) {
        return null == numb;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null?true:(obj instanceof CharSequence?((CharSequence)obj).length() == 0:(obj instanceof Collection?((Collection)obj).isEmpty():(obj instanceof Map?((Map)obj).isEmpty():(obj.getClass().isArray()?Array.getLength(obj) == 0:false))));
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
