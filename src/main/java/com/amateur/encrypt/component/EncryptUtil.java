package com.amateur.encrypt.component;

/**
 * @author yeyu
 * @since 2022/2/28 13:43
 * 加解密工具类接口
 */
public interface EncryptUtil {
    String encrypt(String original);

    String decrypt(String original);
}
