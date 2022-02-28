package com.amateur.encrypt.domain;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yeyu
 * @since 2022/2/28 13:59
 */
@Data
public class VO {
    @EncryptField
    private String desc1 = "abc";

    private String desc2 = "abc";

    private List<InnerVO> list = Arrays.asList(new InnerVO(),new InnerVO(),new InnerVO());

    private Map<String,InnerVO> map = new HashMap<String,InnerVO>() {{
        put("a",new InnerVO());
        put("b",new InnerVO());
    }};



}
