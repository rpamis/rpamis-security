package com.rpamis.security.core.factory;

import com.rpamis.security.annotation.Masked;

/**
 * 脱敏函数接口
 *
 * @author benym
 * @since 2023/10/8 17:26
 */
@FunctionalInterface
public interface MaskFunction {

	/**
	 * 脱敏
	 * @param unMaskInput unMaskInput
	 * @param annotation Masked注解
	 * @return String
	 */
	String mask(String unMaskInput, Masked annotation);

}
