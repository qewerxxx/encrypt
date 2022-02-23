package com.amateur.encrypt.config;

import com.amateur.encrypt.aspect.DataDecryptAspect;
import com.amateur.encrypt.aspect.DataQueryAspect;
import com.amateur.encrypt.utils.AbstractEncDec;
import com.amateur.encrypt.utils.DefaultEncDecInstance;
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
    @ConditionalOnBean(AbstractEncDec.class)
    public DataQueryAspect dataSaveAspect() {
        return new DataQueryAspect();
    }


    @Bean
    @ConditionalOnBean(AbstractEncDec.class)
    public DataDecryptAspect dataDecryptAspect() {
        return new DataDecryptAspect();
    }

}
