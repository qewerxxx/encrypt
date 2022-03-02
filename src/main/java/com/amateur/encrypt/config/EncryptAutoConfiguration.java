package com.amateur.encrypt.config;

import com.amateur.encrypt.aspect.DataDecryptAspect;
import com.amateur.encrypt.aspect.DataEncryptAspect;
import com.amateur.encrypt.component.AESUtils;
import com.amateur.encrypt.component.AbstractEncDec;
import com.amateur.encrypt.component.DefaultEncDecInstance;
import com.amateur.encrypt.component.EncryptUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 启动容器时将切面自动注入
 *
 * @author yeyu
 */
@Configuration
public class EncryptAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AbstractEncDec.class)
    public AbstractEncDec defaultEncDecInstance() {
        return new DefaultEncDecInstance();
    }

    @Bean
    @ConditionalOnMissingBean(EncryptUtil.class)
    public EncryptUtil defaultEncryptUtil() {
        return new AESUtils();
    }

    @Bean
    @ConditionalOnBean(AbstractEncDec.class)
    public DataEncryptAspect dataSaveAspect() {
        return new DataEncryptAspect();
    }


    @Bean
    @ConditionalOnBean(AbstractEncDec.class)
    public DataDecryptAspect dataDecryptAspect() {
        return new DataDecryptAspect();
    }

}
