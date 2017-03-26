package qlw.util;

/**
 * Created by wiki on 2017/1/27.
 */


import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public abstract class MyUtils {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final Pattern PROPERTY = Pattern.compile("([a-z])([A-Z])");
    public static final Pattern CHECK = Pattern.compile("[0-9A-Za-z]{2,32}?");

    public static <T> T getValue(T value, T dv) {
        return (value == null) ? dv : value;
    }

    public static Object getValue(String value, Class dv) {
        if (StringUtils.hasText(value)) {
            Class toClass = (dv == null) ? Object.class : dv.getClass();
            return SimpleTypeConverter.convertValue(value, toClass);
        }
        return dv;
    }

    public static boolean isEnum(Class<Enum> clazz, String name) {
        try {
            Enum.valueOf(clazz, name);
            return true;
        } catch (Throwable localThrowable) {
        }
        return false;
    }

    public static <T> T getProperty(Object root, String property) {
        return null;
    }

    public static String getNextLevelCode(String parentCode, String maxLevelCode) {
        parentCode = (String) getValue(parentCode, "");
        maxLevelCode = (String) getValue(maxLevelCode, parentCode + "-1");
        Assert.isTrue(parentCode.length() % 2 == 0, "parentCode长度必须为偶数");
        Assert.isTrue(maxLevelCode.length() >= 2, "levelCode长度必须不小于2");
        Assert.isTrue(maxLevelCode.length() % 2 == 0, "levelCode长度必须为偶数");

        boolean lengthR = maxLevelCode.length() == parentCode.length() + 2;
        Assert.isTrue(lengthR, "levelCode长度=parentCode+2");
        String prefix = maxLevelCode.substring(0, parentCode.length());
        Assert.isTrue(prefix.equals(parentCode), "levelCode=parentCode##");

        Long nextNumber = Long.valueOf(new Long(maxLevelCode.substring(parentCode.length())).longValue() + 1L);
        String next = parentCode + new DecimalFormat("00").format(nextNumber);

        return next;
    }

    private static char nextChar(char x) {
        if (x == '9')
            x = 'A';
        else if (x == 'Z')
            x = 'a';
        else {
            x = (char) (x + '\001');
        }
        return x;
    }

    public static String getNextLevelCode(String currentCode) {
        Assert.notNull(currentCode, "当前代码不可以为空");

        int currentCodeLength = currentCode.length();
        if ((CHECK.matcher(currentCode).matches()) && (currentCodeLength % 2 == 0)) {
            StringBuilder sb = new StringBuilder(currentCode.substring(0, currentCodeLength - 2));
            char decade = currentCode.charAt(currentCodeLength - 2);
            char single = currentCode.charAt(currentCodeLength - 1);

            if (('z' == single) && ('z' == decade))
                sb.append("00");
            else if ('z' == single) {
                sb.append(nextChar(decade)).append("0");
            } else sb.append(decade).append(nextChar(single));

            return sb.toString();
        }
        throw new IllegalArgumentException(currentCode + ":非法编码");
    }

    public static <T> T buildObject(Class<T> clazz) {
        Assert.notNull(clazz, "需要实例化的类对象不能为NULL");
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("实例化类对象异常", e);
        }
    }

    public static <T> Class<T> getGenericClass(Class<?> genericClass) {
        return getGenericClass(genericClass, 0);
    }

    public static <T> Class<T> getGenericClass(Class<?> genericClass, int index) {
        Class modelClass = null;
        TypeVariable[] tv = genericClass.getTypeParameters();
        if (tv.length == 0) {
            Type gsc = genericClass.getGenericSuperclass();
            if (gsc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) gsc;
                modelClass = (Class) pt.getActualTypeArguments()[index];
            } else {
                modelClass = getGenericClass((Class) gsc);
            }
        } else {
            modelClass = getGenericClass(genericClass, index);
        }
        return modelClass;
    }

    public static <T> Class<T> getGenericClass(Class<?> genericClass, String name) {
        Class modelClass = null;

        TypeVariable[] tv = genericClass.getTypeParameters();
        Map map = new HashMap();
        for (TypeVariable typeVariable : tv) {
            Type type = typeVariable.getBounds()[0];
            if (type instanceof Class) {
                map.put(typeVariable.getName(), (Class) type);
            } else if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                map.put(typeVariable.getName(), (Class) rawType);
            } else {
                throw new RuntimeException("不支持的泛型反射类:" + type);
            }
        }
        itr(genericClass, map);
        if (map.containsKey(name))
            modelClass = (Class) map.get(name);
        else {
            throw new RuntimeException("无法获取指定的泛型类相应的类型：" + name);
        }
        return modelClass;
    }

    private static void itr(Class<?> genericClass, Map<String, Class<?>> map) {
        Class superclass = genericClass.getSuperclass();
        if (Object.class.equals(superclass)) {
            return;
        }

        TypeVariable[] tv = superclass.getTypeParameters();
        Type[] actualTypes = (Type[]) null;
        if (tv.length > 0) {
            Type gsc = genericClass.getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) gsc;
            actualTypes = pt.getActualTypeArguments();
        }
        for (int i = 0; i < tv.length; ++i) {
            if (map.containsKey(tv[i].getName())) {
                continue;
            }
            if (actualTypes[i] instanceof Class)
                map.put(tv[i].getName(), (Class) actualTypes[i]);
            else if (actualTypes[i] instanceof TypeVariable) {
                map.put(tv[i].getName(), (Class) ((TypeVariable) actualTypes[i]).getBounds()[0]);
            }

            map.put(i + "", (Class) map.get(tv[i].getName()));
        }
    }

    public static String mapperColName(String propName) {
        return PROPERTY.matcher(propName).replaceAll("$1_$2").toUpperCase();
    }

    public static WebApplicationContext getAppCtx(Map<String, Object> app) {
        String appCtxKey = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        return (WebApplicationContext) app.get(appCtxKey);
    }

    public static String removeBreakingWhitespace(String original) {
        StringTokenizer whitespaceStripper = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        while (whitespaceStripper.hasMoreTokens()) {
            builder.append(whitespaceStripper.nextToken());
            builder.append(" ");
        }
        return builder.toString();
    }


    /**
     * 获取32位GUID
     *
     * @return
     */
    public static String getUUID() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            UUID uuid = UUID.randomUUID();
            String guidStr = uuid.toString();
            md.update(guidStr.getBytes(), 0, guidStr.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 提取Request中的所有参数并存为Map
     *
     * @param request
     * @return Map
     */
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 根据日期获得所在周的日期
     * @param flag 0当前星期 ；1前一周  ;-1:后一周
     * @param mdate
     * @return
     */
    public static List<Date> dateToWeek(Date mdate,int flag) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mdate);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        mdate=calendar.getTime();
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - (b+flag*7) * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a-1, fdate);
        }
        return list;
    }
}
