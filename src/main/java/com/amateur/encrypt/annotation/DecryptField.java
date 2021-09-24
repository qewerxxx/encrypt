package com.amateur.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 对加密字段进行解密处理
 * @author amateur
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptField {
    /**
     * 正则表达式过滤条件 如果该属性符合正则表达式则直接忽略
     */
    String regex() default "";
}
