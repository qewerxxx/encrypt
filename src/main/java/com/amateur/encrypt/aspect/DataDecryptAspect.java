package com.amateur.encrypt.aspect;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.utils.AbstractEncDec;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 对字段进行解密处理 相较于脱敏切面优先执行
 *
 * @author yeyu
 */
@Aspect
@Slf4j
@Component
@Order(2)
public class DataDecryptAspect {

    @Resource
    private AbstractEncDec defaultEncDecInstance;

    @Pointcut("@annotation(com.amateur.encrypt.annotation.DataSecurity)")
    public void encryptAspect() {
    }

    @AfterReturning(pointcut = "encryptAspect()", returning = "object")
    public Object doAfterReturning(Object object) throws Exception {
        defaultEncDecInstance.decryptField(object, DecryptField.class);
        return object;
    }
}
