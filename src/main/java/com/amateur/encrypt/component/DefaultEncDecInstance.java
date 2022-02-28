package com.amateur.encrypt.component;

import com.amateur.encrypt.constant.BaseEnum;
import com.amateur.encrypt.constant.EncDecType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @author yeyu
 * @since 2022/2/23 11:24
 */
@Component
public class DefaultEncDecInstance extends AbstractEncDec {

    @Resource
    private EncryptUtil aesUtils;

    @Override
    public void doForField(Field field, Object source, Object fieldObj, BaseEnum type) throws Exception {
        String original = (String) fieldObj;
        String after = original;
        if (type.equals(EncDecType.ENCRYPT)) {
            after = aesUtils.encrypt(original);
        } else if (type.equals(EncDecType.DECRYPT)) {
            after = aesUtils.decrypt(original);
        }
        field.set(source, after);
    }

}
