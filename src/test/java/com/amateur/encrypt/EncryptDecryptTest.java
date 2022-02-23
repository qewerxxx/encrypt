package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.utils.DefaultEncDecInstance;

/**
 * @author yeyu
 * @since 2022/2/23 11:41
 */
public class EncryptDecryptTest {
    public static void main(String[] args) throws Exception{
        DefaultEncDecInstance instance = new DefaultEncDecInstance();
        Demo01 demo01 = new Demo01();
        demo01.setAge(10);
        demo01.setName("张三");
        demo01.setPhone("123456767");
//        instance.encryptField(demo01, EncryptField.class);
        System.out.println(demo01);

        Demo02 demo02 = new Demo02();
        demo02.setDemo01(demo01);
        demo02.setDesc("ABC");
        instance.encryptField(demo02,EncryptField.class);
        System.out.println(demo02);
    }
}
