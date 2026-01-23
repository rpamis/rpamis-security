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
import com.rpamis.security.annotation.NestedMasked;
import com.rpamis.security.mask.MaskType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author benym
 * @since 2023/9/8 17:51
 */
@Data
public class TestNestVO implements Serializable {

	private static final long serialVersionUID = -5559148350211559748L;

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
	 * 嵌套校验-直接返回实体
	 */
	@NestedMasked
	private TestVO testVO;

	/**
	 * 嵌套校验-返回List
	 */
	@NestedMasked
	private List<TestVO> testVOList;

	/**
	 * 嵌套校验-返回Map
	 */
	@NestedMasked
	private Map<String, TestVO> testVOMap;

}
