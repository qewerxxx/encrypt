package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yeyu
 * @since 2022/2/23 11:42
 */
@Getter
@Setter
public class Demo01 {
    @EncryptField
    private String name = "ab";

    private Integer age = 10;

    @EncryptField
    private String phone = "ac";

    private Date date = new Date();

    private BigDecimal bigDecimal = BigDecimal.ONE;

    private Demo02 demo02;

    @Override
    public String toString() {
        return "Demo01{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", date=" + date +
                ", bigDecimal=" + bigDecimal +
                ", demo02=" + demo02.hashCode() +
                '}';
    }
}
