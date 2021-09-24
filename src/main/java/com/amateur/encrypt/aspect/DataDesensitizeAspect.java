package com.amateur.encrypt.aspect;

import com.amateur.encrypt.annotation.DesensitizeField;
import com.amateur.encrypt.utils.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 对指定参数进行脱敏
 * @author amateur
 */
@Aspect
@Slf4j
@Component
@Order(1)
public class DataDesensitizeAspect {


    @Pointcut("@annotation(com.amateur.encrypt.annotation.DataSecurity)")
    public void encryptAspect() {
    }

    @AfterReturning(pointcut = "encryptAspect()", returning = "object")
    public Object doAfterReturning(Object object) throws Exception {
        // 针对加密数据的脱敏 线进行解密 在进行脱敏
        AnnotationUtils.decryptAnnotationField(object, DesensitizeField.class);
        AnnotationUtils.desensitizeAnnotationField(object);
        return object;
    }
}
