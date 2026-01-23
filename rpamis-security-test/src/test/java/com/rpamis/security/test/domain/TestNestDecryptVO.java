package com.rpamis.security.test.domain;

import com.rpamis.security.annotation.SecurityField;
import lombok.Data;

/**
 * 测试嵌套解密VO
 *
 * @author benym
 * @since 2026/1/12 19:20
 */
@Data
public class TestNestDecryptVO {

	/**
	 * name
	 */
	@SecurityField
	private String name;

	/**
	 * testVersionDO
	 */
	private TestVersionDO testVersionDO;

	/**
	 * testNestDO
	 */
	private TestNestDO testNestDO;

}
