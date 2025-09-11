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
	 * 主键id
	 */
	private Long id;

	/**
	 * 姓名
	 */
	@Masked(type = MaskType.NAME_MASK)
	private String name;

	/**
	 * 身份证号
	 */
	@Masked(type = MaskType.IDCARD_MASK)
	private String idCard;

	/**
	 * 手机号
	 */
	@Masked(type = MaskType.PHONE_MASK)
	private String phone;

	/**
	 * 自定义标识字段
	 */
	@Masked(type = MaskType.CUSTOM_MASK, start = 2, end = 5, symbol = "#")
	private String customFiled;

}