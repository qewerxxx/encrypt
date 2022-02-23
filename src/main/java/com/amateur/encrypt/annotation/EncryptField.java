package com.amateur.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 加密字段注解
 * 标记改字段需要加密处理
 *
 * @author yeyu
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {
    /**
     * 正则表达式过滤条件 如果该属性符合正则表达式则直接忽略
     */
    String regex() default "";
}
