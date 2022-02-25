package com.amateur.encrypt.utils;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.constant.EncDecType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author yeyu
 * @since 2022/2/23 10:17
 */
@SuppressWarnings("all")
public abstract class AbstractEncDec {

    private final static int MAX_STACK_LENGTH = 100;

    protected abstract String encrypt(String original);

    protected abstract String decrypt(String original);

    /**
     * 检查该类是否是Java核心类
     * @param clazz
     * @return
     */
    private boolean typeCheck(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        // Java核心类库 其类加载器（BootstrapClassloader）为null
        return clazz.getClassLoader() != null && !clazz.isEnum();
    }

    private boolean regexCheck(String str, Annotation annotation) {
        if (annotation instanceof EncryptField) {
            String regex = ((EncryptField) annotation).regex();
            return Pattern.matches(regex, str);
        } else if (annotation instanceof DecryptField) {
            String regex = ((DecryptField) annotation).regex();
            return Pattern.matches(regex, str);
        }
        return false;
    }

    // 处理流程: Obj -> 是否Map,List --是-->①遍历值 -->从头开始
    //                             --否-->②遍历属性值-->是否为加了注解得String字符串--是-->加密解密
    //                                                                       --否-->是否是Map,List--是-->①
    //                                                                                         --否-->是否自定义类--是-->②
    //                                                                                                         --否-->结束
    private void recursive(Object obj,
                           Class<? extends Annotation> annotationClass,
                           Set<Object> set,
                           EncDecType type) throws Exception {
        if (obj == null || annotationClass == null) {
            return;
        }
        if (obj instanceof Collection) {
            Collection list = (Collection) obj;
            for (Object item : list) {
                recursive(item, annotationClass, set, type);
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            for (Object value : map.values()) {
                recursive(value, annotationClass, set, type);
            }
        } else if (typeCheck(obj.getClass())) {
            for (Field field : findFileds(obj.getClass())) {
                field.setAccessible(true);
                doRecursive(obj, field.get(obj), field, annotationClass, 0, set, type);
            }
        }
    }


    /**
     * 递归非Java核心类的对象的所有属性 找到需要加密(解密)的字段
     * @param source            源对象
     * @param fieldObj          源对象中的属性对象
     * @param field             属性
     * @param annotationClass   需要处理的注解
     * @param count             递归深度
     * @param set               已遍历对象集合 防止循环引用
     * @param type              加密 解密操作类型
     * @throws Exception
     */
    private void doRecursive(Object source,
                             Object fieldObj,
                             Field field,
                             Class<? extends Annotation> annotationClass,
                             int count,
                             Set<Object> set,
                             EncDecType type) throws Exception {
        if (fieldObj == null) {
            return;
        }
        if (count > MAX_STACK_LENGTH) {
            throw new RuntimeException("递归过深");
        }
        if (fieldObj instanceof String) {
            if (field.isAnnotationPresent(annotationClass)
                    && !regexCheck(fieldObj.toString(), field.getAnnotation(annotationClass))) {
                doForField(field, source, fieldObj, type);
            }
        } else if ((fieldObj instanceof Collection) || (fieldObj instanceof Map)) {
            recursive(fieldObj, annotationClass, set, type);
        } else if (typeCheck(fieldObj.getClass())) {
            for (Field inFiled : findFileds(fieldObj.getClass())) {
                inFiled.setAccessible(true);
                if (set.contains(fieldObj)) {
                    return;
                } else {
                    set.add(fieldObj);
                }
                doRecursive(fieldObj, inFiled.get(fieldObj), inFiled, annotationClass, ++count, set, type);
            }
        }
    }

    /**
     * 加解密
     * @param field
     * @param source
     * @param fieldObj
     * @param type
     * @throws Exception
     */
    private void doForField(Field field, Object source, Object fieldObj, EncDecType type) throws Exception {
        String original = (String) fieldObj;
        String after = original;
        if (type.equals(EncDecType.ENCRYPT)) {
            after = encrypt(original);
        } else if (type.equals(EncDecType.DECRYPT)) {
            after = decrypt(original);
        }
        field.set(source, after);
    }

    /**
     * 找到该类的所有属性 包括父类私有属性
     * @param clazz
     * @return
     */
    private Field[] findFileds(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                list.add(field);
            }
        } while ((clazz = clazz.getSuperclass()) != Object.class);
        return list.toArray(new Field[list.size()]);
    }

    public void decryptField(Object obj, Class<? extends Annotation> annotationClass) throws Exception {
        recursive(obj, annotationClass, new HashSet<>(), EncDecType.DECRYPT);
    }

    public void encryptField(Object obj, Class<? extends Annotation> annotationClass) throws Exception {
        recursive(obj, annotationClass, new HashSet<>(), EncDecType.ENCRYPT);
    }

}
