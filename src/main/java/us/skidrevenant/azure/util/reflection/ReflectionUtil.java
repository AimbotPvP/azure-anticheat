package us.skidrevenant.azure.util.reflection;

import java.lang.reflect.Field;

/**
 * @author SkidRevenant
 * at 28/05/2018
 */
public class ReflectionUtil {

    public static Field getField(Class<?> clazz, String name, Class<?> type) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            if (field.getType() != type) {
                throw new IllegalStateException("Invalid type for field '" + name + "' (expected " + type.getName() + ", got " + field.getType().getName() + ")");
            }

            return field;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field '" + name + "'");
        }
    }

    public static <T> T getFieldValue(Field field, Object instance) {
        try {
            //noinspection unchecked
            field.setAccessible(true);

            return (T) field.get(instance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get value of field '" + field.getName() + "'");
        }
    }
}
