package com.amateur.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 数据安全 方法级别注解 使用此注解表示该类中字段有以下几种情况:
 * 1. 加密字段解密
 *
 * @author yeyu
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataDecryptSecurity {
}
