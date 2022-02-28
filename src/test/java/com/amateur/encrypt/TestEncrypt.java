package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import com.amateur.encrypt.component.AbstractEncDec;
import com.amateur.encrypt.config.EncryptAutoConfiguration;
import com.amateur.encrypt.constant.EncDecType;
import com.amateur.encrypt.constant.ExtraEnum;
import com.amateur.encrypt.domain.VO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yeyu
 * @since 2022/2/28 13:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EncryptAutoConfiguration.class,TestEncrypt.class})
@ComponentScan("com.amateur.encrypt")
public class TestEncrypt {

    @Autowired
    private AbstractEncDec abstractEncDec;

    @Test
    public void testEnc() throws Exception {
        VO vo = new VO();
        abstractEncDec.doActive(vo, EncryptField.class, EncDecType.ENCRYPT);
        System.out.println(vo);
    }

    @Test
    public void testDec() throws Exception {
        VO vo = new VO();
        abstractEncDec.doActive(vo, EncryptField.class, EncDecType.ENCRYPT);
        abstractEncDec.doActive(vo, EncryptField.class, EncDecType.DECRYPT);
        System.out.println(vo);
    }

    @Test
    public void testCus() throws Exception {
        VO vo = new VO();
        abstractEncDec.doActive(vo, EncryptField.class, ExtraEnum.A);
        System.out.println(vo);
    }
}
