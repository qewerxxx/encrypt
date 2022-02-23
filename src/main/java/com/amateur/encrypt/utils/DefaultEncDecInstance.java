package com.amateur.encrypt.utils;

import org.springframework.stereotype.Component;

/**
 * @author yeyu
 * @since 2022/2/23 11:24
 */
@Component
public class DefaultEncDecInstance extends AbstractEncDec {
    @Override
    public String decrypt(String original) {
        return AESUtils.decrypt(original);
    }

    @Override
    public String encrypt(String original) {
        return AESUtils.encrypt(original);
    }
}
