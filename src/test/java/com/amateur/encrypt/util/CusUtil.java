package com.amateur.encrypt.util;

import com.amateur.encrypt.component.EncryptUtil;
import org.springframework.stereotype.Component;

/**
 * @author yeyu
 * @since 2022/2/28 14:11
 */
//@Component
public class CusUtil implements EncryptUtil {
    @Override
    public String encrypt(String original) {
        return "";
    }

    @Override
    public String decrypt(String original) {
        return "ok";
    }
}
