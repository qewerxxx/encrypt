package com.amateur.encrypt.aspect;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.utils.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 对字段进行解密处理 相较于脱敏切面优先执行
 * @author amateur
 */
@Aspect
@Slf4j
@Component
@Order(2)
public class DataDecryptAspect {

    @Pointcut("@annotation(com.amateur.encrypt.annotation.DataSecurity)")
    public void encryptAspect() {
    }

    @AfterReturning(pointcut = "encryptAspect()", returning = "object")
    public Object doAfterReturning(Object object) throws Exception {
        AnnotationUtils.decryptAnnotationField(object, DecryptField.class);
        return object;
    }
}
