package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yeyu
 * @since 2022/2/23 13:38
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Demo03 {
    @EncryptField
    private String desc;
}
