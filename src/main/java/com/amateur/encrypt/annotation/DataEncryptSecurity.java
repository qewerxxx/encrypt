package com.amateur.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 数据安全 方法级别注解 使用此注解表示该类中字段有以下几种情况:
 * 1. 查询参数中有字段需要加密
 * 2. 需要存储的敏感字段加密
 *
 * @author yeyu
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataEncryptSecurity {
}
