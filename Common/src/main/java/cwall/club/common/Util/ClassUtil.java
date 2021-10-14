package cwall.club.common.Util;

import java.lang.reflect.Field;

public class ClassUtil {
    public static void copyOneFromOne(Object to,Object from,Class toClass,Class fromClass){
        Field[] fields = fromClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Field declaredField = null;
            try {
                declaredField = toClass.getDeclaredField(name);
                declaredField.setAccessible(true);
                declaredField.set(to, field.get(from));
            } catch (NoSuchFieldException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            }
        }
    }
}
