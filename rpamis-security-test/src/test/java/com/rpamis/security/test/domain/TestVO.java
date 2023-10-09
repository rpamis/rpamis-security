package com.rpamis.security.test.domain;

import com.rpamis.security.annotation.Masked;
import com.rpamis.security.mask.MaskType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author benym
 * @date 2023/9/7 17:19
 */
@Data
public class TestVO implements Serializable {

    private static final long serialVersionUID = 1142843493987112387L;

    /**
     *
     */
    private Long id;

    /**
     *
     */
    @Masked(type = MaskType.NAME_MASK)
    private String name;

    /**
     *
     */
    @Masked(type = MaskType.IDCARD_MASK)
    private String idCard;

    /**
     *
     */
    @Masked(type = MaskType.PHONE_MASK)
    private String phone;


    @Masked(type = MaskType.CUSTOM_MASK, start = 2, end = 5, symbol = "#")
    private String customFiled;
}
