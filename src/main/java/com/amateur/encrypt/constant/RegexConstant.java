package com.amateur.encrypt.constant;

/**
 * 脱敏常量类
 * @author amateur
 */
public interface RegexConstant {
    /**
     * 电话号码中间4位脱敏
     */
    String PHONE = "(\\d{3})\\d{4}(\\d{4})";
    String PHONE_REPLACE = "$1****$2";

    /**
     * 身份证前三后四 中间脱敏
     */
    String IDCARD = "(?<=\\w{3})\\w(?=\\w{4})";
    String IDCARD_REPLACE = "*";

    /**
     * 人名脱敏
     * 只保留姓
     */
    String NAME = "(?<=.).";
    String NAME_REPLACE = "*";

    /**
     * 地址脱敏
     * 全部*代替
     */
    String ADDRESS = "(.*)";
    String ADDRESS_REPLACE = "*";

}
