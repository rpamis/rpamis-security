package com.rpamis.security.test.domain;

import com.rpamis.security.annotation.Masked;
import com.rpamis.security.annotation.NestedMasked;
import com.rpamis.security.mask.MaskType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author benym
 * @date 2023/9/8 17:51
 */
@Data
public class TestNestVO implements Serializable {

    private static final long serialVersionUID = -5559148350211559748L;

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
     * 嵌套校验
     */
    @NestedMasked
    private TestVO testVO;

    @NestedMasked
    private List<TestVO> testVOList;

    @NestedMasked
    private Map<String, TestVO> testVOMap;
}
