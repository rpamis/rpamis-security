package com.rpamis.security.test.domain;

import com.rpamis.security.annotation.Masked;
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
    @Masked()
    private String name;

    /**
     *
     */
    @Masked()
    private String idCard;

    /**
     *
     */
    @Masked()
    private String phone;
}
