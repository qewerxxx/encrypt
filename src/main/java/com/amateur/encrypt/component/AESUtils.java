package com.amateur.encrypt.component;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

/**
 * 功能描述:  加密工具
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class AESUtils implements EncryptUtil {

    /**
     * 密钥动态获取秘钥(通过环境变量获取)
     */
    public static String KEY = System.getenv("ENCRYPTION_KEY");

    private static final String CHARSET = "utf-8";
    /**
     * 偏移量
     */
    private static final int OFFSET = 16;
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    static {
        if (Objects.isNull(KEY)) {
            KEY = "ABCXVMbb9zjABCXGcv6kDoWEAdtfPXXX";
        }
    }


    /**
     * 加密
     */
    public String encrypt(String content) {
        return encrypt(content, KEY);
    }

    /**
     * 解密
     */
    public String decrypt(String content) {
        return decrypt(content, KEY);
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     加密密码
     * @return
     */
    public String encrypt(String content, String key) {
        checkKey(key);
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, OFFSET);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] byteContent = content.getBytes(CHARSET);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
            byte[] result = cipher.doFinal(byteContent);
            // 加密
            return new Base64().encodeToString(result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void checkKey(String key) {
        if (Objects.isNull(key)) {
            throw new RuntimeException("加解密Key不能为空。");
        }
    }

    /**
     * AES（256）解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return 解密之后
     */
    public String decrypt(String content, String key) {
        checkKey(key);
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, OFFSET);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, skey, iv);
            byte[] result = cipher.doFinal(new Base64().decode(content));
            // 解密
            return new String(result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
