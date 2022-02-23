package com.amateur.encrypt.utils;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.constant.EncDecType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author yeyu
 * @since 2022/2/23 10:17
 */
@SuppressWarnings("all")
public abstract class AbstractEncDec {

    private final static int MAX_STACK_LENGTH = 100;

    private EncDecType type;

    protected String encrypt(String original) {
        throw new UnsupportedOperationException();
    }

    protected String decrypt(String original) {
        throw new UnsupportedOperationException();
    }

    private void setType(EncDecType type) {
        this.type = type;
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

    private void recursive(Object obj, Class<? extends Annotation> annotationClass) throws Exception {
        if (obj == null || annotationClass == null) {
            return;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (Object item : list) {
                if (typeCheck(item.getClass())) {
                    for (Field field : item.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        doRecursive(item,field.get(item), field, annotationClass, 0);
                    }
                }
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            for (Object value : map.values()) {
                if (typeCheck(value.getClass())) {
                    for (Field field : value.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        doRecursive(value,field.get(value), field, annotationClass, 0);
                    }
                }
            }
        } else if (typeCheck(obj.getClass())) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                doRecursive(obj,field.get(obj), field, annotationClass, 0);
            }
        }
    }


    private void doRecursive(Object source,Object fieldObj, Field field, Class<? extends Annotation> annotationClass, int count) throws Exception {
        if (fieldObj == null) {
            return;
        }
        if (count > MAX_STACK_LENGTH) {
            throw new RuntimeException();
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
        } else if (typeCheck(fieldObj.getClass())) {
            for (Field inFiled : fieldObj.getClass().getDeclaredFields()) {
                inFiled.setAccessible(true);
                doRecursive(fieldObj,inFiled.get(fieldObj), inFiled, annotationClass, ++count);
            }
        }
    }

    public void decryptField(Object obj,Class<? extends Annotation> annotationClass) throws Exception {
        setType(EncDecType.DECRYPT);
        recursive(obj, annotationClass);
    }

    public void encryptField(Object obj,Class<? extends Annotation> annotationClass) throws Exception {
        setType(EncDecType.ENCRYPT);
        recursive(obj, annotationClass);
    }

}
