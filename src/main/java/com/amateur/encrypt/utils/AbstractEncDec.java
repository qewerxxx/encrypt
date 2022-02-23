package com.amateur.encrypt.utils;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.constant.EncDecType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author yeyu
 * @since 2022/2/23 10:17
 */
@SuppressWarnings("all")
public abstract class AbstractEncDec {

    private final static int MAX_STACK_LENGTH = 100;

    protected String encrypt(String original) {
        throw new UnsupportedOperationException();
    }

    protected String decrypt(String original) {
        throw new UnsupportedOperationException();
    }

    private boolean typeCheck(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        // Java 自带的类的classLoader都为null
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
        if (obj instanceof List) {
            List list = (List) obj;
            for (Object item : list) {
                recursive(item, annotationClass, set, type);
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            for (Object value : map.values()) {
                recursive(value, annotationClass, set, type);
            }
        } else if (typeCheck(obj.getClass())) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                doRecursive(obj, field.get(obj), field, annotationClass, 0, set, type);
            }
        }
    }


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
                String original = (String) fieldObj;
                String after = original;
                if (type.equals(EncDecType.ENCRYPT)) {
                    after = encrypt(original);
                } else if (type.equals(EncDecType.DECRYPT)) {
                    after = decrypt(original);
                }
                field.set(source, after);
            }
        } else if ((fieldObj instanceof List) || (fieldObj instanceof Map)) {
            recursive(fieldObj, annotationClass, set, type);
        } else if (typeCheck(fieldObj.getClass())) {
            for (Field inFiled : fieldObj.getClass().getDeclaredFields()) {
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

    public void decryptField(Object obj, Class<? extends Annotation> annotationClass) throws Exception {
        recursive(obj, annotationClass, new HashSet<>(), EncDecType.DECRYPT);
    }

    public void encryptField(Object obj, Class<? extends Annotation> annotationClass) throws Exception {
        recursive(obj, annotationClass, new HashSet<>(), EncDecType.ENCRYPT);
    }

}
