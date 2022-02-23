package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.utils.DefaultEncDecInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeyu
 * @since 2022/2/23 11:41
 */
public class EncryptDecryptTest {
    public static void main(String[] args) throws Exception {
        DefaultEncDecInstance instance = new DefaultEncDecInstance();

        List<Demo02> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Demo02());
        }

        long s = System.currentTimeMillis();
        for (Demo02 demo02 : list) {
            instance.encryptField(demo02, EncryptField.class);
            System.out.println(demo02);
        }
        long e = System.currentTimeMillis();
        System.out.println(e-s);

    }
}
