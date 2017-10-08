package com.github.chencye.app.file2db.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectUtils {
    private ReflectUtils() {
        throw new UnsupportedOperationException();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * <pre>
     * 将from中各字段值拷贝到to相应字段中，由isDiffCopy控制 是否拷贝非null且非equals字段的拷贝
     * 0. 无读或无写方法，忽略
     * 1. from字段值为null，忽略
     * 2. to字段值为null，拷贝
     * 3. 字段值相同，忽略
     * 4. 字段值不相同，由selfPackName控制是否为自定义类型
     * 5. 非自定义类型，由isDiffCopy控制是否拷贝；自定义类型，深层拷贝
     * </pre>
     *
     * @param from         源，对其各字段判断空。若非空，判断是否需要深层拷贝，不需要则输出字段值
     * @param to           目标，接收from不为空的字段值
     * @param selfPackName 自定义类包名，这种包下的类，会进行深层拷贝。为null时，不进行深层拷贝
     * @param isDiffCopy   是否拷贝 非null且非equals字段
     */
    public static <T> void copyProperties(T from, T to, String selfPackName, boolean isDiffCopy) {
        if (from == null || to == null) {
            return;
        }
        try {
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(from.getClass());
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 0. 无读或无写方法，忽略
                if (readMethod == null || writeMethod == null) {
                    continue;
                }

                Object fromValue = readMethod.invoke(from);
                // 1. from字段值为null，忽略
                if (fromValue == null) {
                    continue;
                }

                Object toValue = readMethod.invoke(to);
                // 2. to字段值为null，拷贝
                if (toValue == null) {
                    writeMethod.invoke(to, fromValue);
                    continue;
                }

                // 3. 字段值相同，忽略
                if (fromValue.equals(toValue)) {
                    continue;
                }

                // 4. 字段值不相同，由selfPackName控制是否为自定义类型
                if (selfPackName == null || !propertyDescriptor.getPropertyType().getName().startsWith(selfPackName)) {
                    // 5. 非自定义类型，由isDiffCopy控制是否拷贝
                    if (isDiffCopy) {
                        writeMethod.invoke(to, fromValue);
                    }
                    continue;
                }

                // 5. 自定义类型，深层拷贝
                copyProperties(fromValue, toValue, selfPackName, isDiffCopy);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.warn("copyProperties error.", e);
        }
    }

    /**
     * <pre>
     * 将from中不为null的字段拷贝到to相应字段中
     *
     * 1. from字段值为null，忽略
     * 2. to字段值为null，拷贝
     * 3. 字段值相同，忽略
     * 4. 字段值不相同，from覆盖to
     * </pre>
     *
     * @param from         源，对其各字段判断空。若非空，判断是否需要深层拷贝，不需要则输出字段值
     * @param to           目标，接收from不为空的字段值
     * @param selfPackName 自定义类包名，这种包下的类，会进行深层拷贝
     */
    public static <T> void copyPropertiesOnNotNull(T from, T to, String selfPackName) {
        copyProperties(from, to, selfPackName, true);
    }

    /**
     * <pre>
     * 将from中不为null的字段拷贝到to相应字段中，取from前三级包名为自定义类型
     *
     * 1. from字段值为null，忽略
     * 2. to字段值为null，拷贝
     * 3. 字段值相同，忽略
     * 4. 字段值不相同，from覆盖to
     * </pre>
     *
     * @param from 源，对其各字段判断空。若非空，判断是否需要深层拷贝，不需要则输出字段值
     * @param to   目标，接收from不为空的字段值
     */
    public static <T> void copyPropertiesOnNotNull(T from, T to) {
        // 取from前三级包名为自定义类型
        String selfPackName = ClassUtils.getRootPackName(from.getClass(), 3);
        copyProperties(from, to, selfPackName, true);
    }

    /**
     * <pre>
     * to中为null的字段，取from中相应字段值
     * 1. from字段值为null，忽略
     * 2. to字段值为null，拷贝
     * 3. 字段值相同，忽略
     * 4. 字段值不相同，忽略
     * </pre>
     *
     * @param from         源，输出相应字段值
     * @param to           目标，对其各字段判断空：为空，则接收from中相应字段值；不为空，进行深层拷贝
     * @param selfPackName 自定义类包名，这种包下的类，会进行深层拷贝
     */
    public static <T> void copyPropertiesOnNull(T from, T to, String selfPackName) {
        copyProperties(from, to, selfPackName, false);
    }

    /**
     * <pre>
     * to中为null的字段，取from中相应字段值，取from前三级包名为自定义类型
     * 1. from字段值为null，忽略
     * 2. to字段值为null，拷贝
     * 3. 字段值相同，忽略
     * 4. 字段值不相同，忽略
     * </pre>
     *
     * @param from 源，输出相应字段值
     * @param to   目标，对其各字段判断空：为空，则接收from中相应字段值；不为空，进行深层拷贝
     */
    public static <T> void copyPropertiesOnNull(T from, T to) {
        // 取from前三级包名为自定义类型
        String selfPackName = ClassUtils.getRootPackName(from.getClass(), 3);
        copyProperties(from, to, selfPackName, false);
    }
}
