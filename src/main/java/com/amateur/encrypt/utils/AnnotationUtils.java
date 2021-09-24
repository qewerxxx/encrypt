package com.amateur.encrypt.utils;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.annotation.DesensitizeField;
import com.amateur.encrypt.annotation.EncryptField;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author yeyu
 */
public class AnnotationUtils {
    /**
     * 最大递归深度
     */
    private final static Integer MAX_LENGTH = 100;

    /**
     * 重载方法 提供外部调用
     *
     * @param obj
     */
    public static void encryptAnnotationField(Object obj,Class<? extends Annotation> annotationClass) throws Exception {
        encryptAnnotationField(obj, 0,annotationClass);
    }

    /**
     * 重载方法 提供外部调用
     *
     * @param obj
     */
    public static void decryptAnnotationField(Object obj, Class<? extends Annotation> annotationClass) throws Exception {
        decryptAnnotationField(obj, 0, annotationClass);
    }

    /**
     * 重载方法 提供外部调用
     *
     * @param obj
     */
    public static void desensitizeAnnotationField(Object obj) throws Exception {
        desensitizeAnnotationField(obj, 0);
    }

    /**
     * 递归为指定加密注解的对象加密
     *
     * @param obj 对象
     */
    public static void encryptAnnotationField(Object obj, int count,Class<? extends Annotation> annotationClass) throws Exception {
        if (obj == null) {
            return;
        }
        if (count >= MAX_LENGTH) {
            throw new RuntimeException("加密异常，递归栈过深，结束递归");
        }
        // List 类型处理
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object o : list) {
                encryptAnnotationField(o, count,annotationClass);
            }
        }
        // Map 类型处理
        else if (obj instanceof Map) {
            Map<?,?> map = (Map<?,?>) obj;
            for (Object value : map.values()) {
                encryptAnnotationField(value, count,annotationClass);
            }
        }
        // 自建类 递归处理
        else if (typeCheck(obj.getClass())) {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                } else if (field.isAnnotationPresent(annotationClass)) {
                    // 正则过滤 匹配的字符串不进行加密/解密
                    if (regexCheck(field.get(obj).toString(), field.getAnnotation(annotationClass))) {
                        continue;
                    }
                    field.set(obj, AESUtils.encrypt(field.get(obj).toString()));
                } else if (field.getType().equals(List.class)) {
                    List<?> list = (List<?>) field.get(obj);
                    for (Object o : list) {
                        encryptAnnotationField(o, count,annotationClass);
                    }
                } else if (field.getType().equals(Map.class)) {
                    Map<?,?> map = (Map<?,?>) field.get(obj);
                    for (Object value : map.values()) {
                        encryptAnnotationField(value, count,annotationClass);
                    }
                } else if (typeCheck(field.get(obj).getClass())) {
                    encryptAnnotationField(field.get(obj), ++count,annotationClass);
                }
            }
        }
    }

    /**
     * 递归解密加了指定注解的对象 (针对List Map)
     *
     * @param obj             对象
     * @param annotationClass 指定注解
     */
    public static void decryptAnnotationField(Object obj, int count, Class<? extends Annotation> annotationClass) throws Exception {
        if (obj == null) {
            return;
        }
        if (count >= MAX_LENGTH) {
            throw new RuntimeException("解密异常，递归栈过深，结束递归");
        }
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object o : list) {
                decryptAnnotationField(o, count, annotationClass);
            }
        } else if (obj instanceof Map) {
            Map<?,?> map = (Map<?,?>) obj;
            for (Object value : map.values()) {
                decryptAnnotationField(value, count, annotationClass);
            }
        } else if (typeCheck(obj.getClass())) {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                } else if (field.isAnnotationPresent(annotationClass)) {
                    if (regexCheck(field.get(obj).toString(), field.getAnnotation(annotationClass))) {
                        continue;
                    }
                    String decrypt = AESUtils.decrypt(field.get(obj).toString());
                    if (!StringUtils.isEmpty(decrypt)) {
                        field.set(obj, decrypt);
                    }
                } else if (field.getType().equals(List.class)) {
                    List<?> list = (List<?>) field.get(obj);
                    for (Object o : list) {
                        decryptAnnotationField(o, count, annotationClass);
                    }
                } else if (field.getType().equals(Map.class)) {
                    Map<?,?> map = (Map<?,?>) field.get(obj);
                    for (Object value : map.values()) {
                        decryptAnnotationField(value, count, annotationClass);
                    }
                } else if (typeCheck(field.get(obj).getClass())) {
                    decryptAnnotationField(field.get(obj), ++count, annotationClass);
                }
            }
        }
    }

    /**
     * 递归为加了脱敏注解的对象脱敏
     *
     * @param obj 对象
     */
    public static void desensitizeAnnotationField(Object obj, int count) throws Exception {
        if (obj == null) {
            return;
        }
        if (count >= MAX_LENGTH) {
            throw new RuntimeException("脱敏异常，递归栈过深，结束递归");
        }
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object o : list) {
                desensitizeAnnotationField(o, count);
            }
        } else if (obj instanceof Map) {
            Map<?,?> map = (Map<?,?>) obj;
            for (Object value : map.values()) {
                desensitizeAnnotationField(value, count);
            }
        } else if (typeCheck(obj.getClass())) {
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                } else if (field.isAnnotationPresent(DesensitizeField.class)) {
                    String regex = field.getAnnotation(DesensitizeField.class).regex();
                    String replace = field.getAnnotation(DesensitizeField.class).replace();
                    String regexString = field.get(obj).toString().replaceAll(regex, replace);
                    if (regexString.length() > 11) {
                        regexString = regexString.substring(0, 11);
                    }
                    field.set(obj, regexString);
                } else if (field.getType().equals(List.class)) {
                    List<?> list = (List<?>) field.get(obj);
                    for (Object o : list) {
                        desensitizeAnnotationField(o, count);
                    }
                } else if (field.getType().equals(Map.class)) {
                    Map<?,?> map = (Map<?,?>) field.get(obj);
                    for (Object value : map.values()) {
                        desensitizeAnnotationField(value, count);
                    }
                } else if (typeCheck(field.get(obj).getClass())) {
                    desensitizeAnnotationField(field.get(obj), ++count);
                }
            }
        }
    }

    /**
     * 正则表达式过滤
     * @param str 目标字符串
     * @param annotation 指定注解 (EncryptField,DecryptField)
     * @return 是否需要过滤
     */
    private static boolean regexCheck(String str, Annotation annotation) {
        if (annotation instanceof EncryptField) {
            String regex = ((EncryptField) annotation).regex();
            return Pattern.matches(regex, str);
        } else if (annotation instanceof DecryptField) {
            String regex = ((DecryptField) annotation).regex();
            return Pattern.matches(regex, str);
        }
        return false;
    }

    /**
     * 类型检测 仅针对自建的类进行递归遍历
     * @param clazz 目标类对象
     * @return 是否为自建类
     */
    private static boolean typeCheck(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        // Java 自带的类的classLoader都为null
        return clazz.getClassLoader() != null && !clazz.isEnum();
    }


}
