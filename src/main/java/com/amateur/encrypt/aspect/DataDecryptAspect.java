package com.amateur.encrypt.aspect;

import com.amateur.encrypt.annotation.DecryptField;
import com.amateur.encrypt.component.AbstractEncDec;
import com.amateur.encrypt.constant.EncDecType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * 对字段进行解密处理 相较于脱敏切面优先执行
 *
 * @author yeyu
 */
@Aspect
@Slf4j
@Order(2)
public class DataDecryptAspect {

    @Resource
    private AbstractEncDec defaultEncDecInstance;

    @Pointcut("@annotation(com.amateur.encrypt.annotation.DataDecryptSecurity)")
    public void encryptAspect() {
    }

    @AfterReturning(pointcut = "encryptAspect()", returning = "object")
    public Object doAfterReturning(Object object) throws Exception {
        defaultEncDecInstance.doActive(object, DecryptField.class, EncDecType.DECRYPT);
        return object;
    }

}
