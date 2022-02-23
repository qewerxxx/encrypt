package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yeyu
 * @since 2022/2/23 11:42
 */
@Data
public class Demo01 {
    @EncryptField
    private String name;

    private Integer age;

    @EncryptField
    private String phone;

    private Date date = new Date();

    private BigDecimal bigDecimal = BigDecimal.ONE;
}
