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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rpamis.security.annotation.SecurityField;
import lombok.Data;

/**
 * 测试嵌套实体
 *
 * @author benym
 * @since 2026/1/12 18:35
 */
@TableName(value = "test_nest")
@Data
public class TestNestDO {

	/**
	 *
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 *
	 */
	@TableField(value = "user_account")
	@SecurityField
	private String userAccount;

}
