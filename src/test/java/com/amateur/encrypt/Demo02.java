package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.Data;

/**
 * @author yeyu
 * @since 2022/2/23 11:43
 */
@Data
public class Demo02 {
    @EncryptField
    private String desc;

    private Demo01 demo01;
}
