package com.amateur.encrypt.aspect;

import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.utils.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 对请求参数加密 对敏感字段加密
 * @author amateur
 */
@Aspect
@Slf4j
@Component
@Order(3)
public class DataQueryAspect {


    @Pointcut("@annotation(com.amateur.encrypt.annotation.DataSecurity)")
    public void encryptAspect() {
    }

    @Before("encryptAspect()")
    public void doBefore(JoinPoint point) throws Exception {
        Object[] args = point.getArgs();
        for (Object arg : args) {
            AnnotationUtils.encryptAnnotationField(arg, EncryptField.class);
        }
    }

    @AfterReturning(value = "encryptAspect()", returning = "object")
    public Object doAfter(Object object) throws Exception {
        // 针对数据库中存在一部分加密 一部分未加密的数据 先进行一次解密 在进行加密 防止对已加密的数据进行二次加密
        AnnotationUtils.decryptAnnotationField(object, EncryptField.class);
        AnnotationUtils.encryptAnnotationField(object, EncryptField.class);
        return object;
    }
}
