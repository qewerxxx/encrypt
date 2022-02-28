package com.amateur.encrypt.util;

import com.amateur.encrypt.component.AbstractEncDec;
import com.amateur.encrypt.constant.BaseEnum;
import com.amateur.encrypt.constant.ExtraEnum;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author yeyu
 * @since 2022/2/28 14:57
 */
@Component
public class CusEncDec extends AbstractEncDec {

    @Override
    protected void doForField(Field field, Object source, Object fieldObj, BaseEnum type) throws Exception {
        ExtraEnum newType = (ExtraEnum) type;

        switch (newType) {
            case A:
            case B:
            case EXTRA:
                System.out.println("ABC");
        }
    }
}
