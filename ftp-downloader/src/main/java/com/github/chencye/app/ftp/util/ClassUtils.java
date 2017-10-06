package com.github.chencye.app.ftp.util;

public class ClassUtils {
    private ClassUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取clazzz的包名，deep为包的深度
     *
     * @param clazz
     * @param deep
     * @return
     */
    public static String getRootPackName(Class<?> clazz, int deep) {
        String className = clazz.getName();
        return className.substring(0, indexOfWhich(className, ".", deep, true));
    }

    /**
     * str中第which个sub的位置
     *
     * @param str
     * @param sub
     * @param which
     * @param isMoveAhead 为true时，若不足n个，侧取n-1个的位置
     * @return
     */
    public static int indexOfWhich(String str, String sub, int which, boolean isMoveAhead) {
        int pos = 0;
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, pos)) != -1 && count <= which) {
            count++;
            pos = idx + sub.length();
        }
        pos -= sub.length();
        if (count != which && !isMoveAhead) {
            pos = -1;
        }
        return pos;
    }

    /**
     * str中第which个sub的位置，若没有whick那么多个，则返回-1
     *
     * @param str
     * @param sub
     * @param which
     * @return
     */
    public static int indexOfWhich(String str, String sub, int which) {
        return indexOfWhich(str, sub, which, false);
    }

}
