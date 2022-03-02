package com.amateur.encrypt.domain;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.Data;

/**
 * @author yeyu
 * @since 2022/2/28 14:04
 */
@Data
public class InnerVO {
    @EncryptField
    private String msg = "abc";
}
