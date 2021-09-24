package com.amateur.encrypt.config;

import com.amateur.encrypt.aspect.DataDecryptAspect;
import com.amateur.encrypt.aspect.DataDesensitizeAspect;
import com.amateur.encrypt.aspect.DataQueryAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 启动容器时将切面自动注入
 * @author amateur
 */
@Configuration
public class EncryptAutoConfiguration {
    @Bean
    public DataDesensitizeAspect dataDesensitizeAspect() {
        return new DataDesensitizeAspect();
    }


    @Bean
    public DataQueryAspect dataSaveAspect() {
        return new DataQueryAspect();
    }


    @Bean
    public DataDecryptAspect dataDecryptAspect() {
        return new DataDecryptAspect();
    }

}
