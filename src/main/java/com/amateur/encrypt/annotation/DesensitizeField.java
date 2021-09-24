package com.amateur.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 标记改属性在返回时需要脱敏处理
 * 脱敏采用的方法就是简单的String.replaceAll()方法
 * replaceAll()的两个参数分别对应regex和replace
 * @author amateur
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DesensitizeField {
    /**
     * 匹配需要脱敏的字符的正则表达式
     * @see com.amateur.encrypt.constant.RegexConstant
     */
    String regex() default "";

    /**
     * 需要替换的字符
     * @see com.amateur.encrypt.constant.RegexConstant
     */
    String replace() default "";
}
