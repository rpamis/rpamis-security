/*
 * Copyright 2023-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rpamis.security.test.domain;

import com.rpamis.security.annotation.Masked;
import com.rpamis.security.mask.MaskType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author benym
 * @since 2023/9/7 17:19
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