package com.amateur.encrypt;

import com.amateur.encrypt.annotation.EncryptField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yeyu
 * @since 2022/2/23 11:43
 */
@Getter
@Setter
@ToString
public class Demo02 {
    @EncryptField
    private String desc;

    private Demo01 demo01;

    private List<Demo03> list = Arrays.asList(new Demo03("a"),new Demo03("b"));

    private List<List<Demo03>> llist = Arrays.asList(Arrays.asList(new Demo03("c")));

    private Map<String,Demo03> map = new HashMap<String, Demo03>() {{
        put("a",new Demo03("x"));
    }};
}
