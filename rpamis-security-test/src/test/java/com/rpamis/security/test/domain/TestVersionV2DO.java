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
import lombok.Data;

import java.io.Serializable;

/**
 * TestVersionV2DO无加解密注解标识的实体
 *
 * @author benym
 * @since 2025/9/10 17:42
 */
@TableName(value = "test_version")
@Data
public class TestVersionV2DO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 姓名
	 */
	@TableField(value = "name")
	private String name;

	/**
	 * 身份证号
	 */
	@TableField(value = "id_card")
	private String idCard;

	/**
	 * 电话
	 */
	@TableField(value = "phone")
	private String phone;

	/**
	 * 版本号
	 */
	@TableField(value = "version")
	private Integer version;

}
