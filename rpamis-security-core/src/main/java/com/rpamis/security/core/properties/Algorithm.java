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
package com.rpamis.security.core.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.StringUtils;

/**
 * 加解密算法实体
 *
 * @author benym
 * @since 2026/1/8 16:32
 */
public class Algorithm {

	private static final Logger LOGGER = LoggerFactory.getLogger(Algorithm.class);

	/**
	 * 全局加解密算法
	 */
	public String active;

	/**
	 * SM4
	 */
	@NestedConfigurationProperty
	public Sm4Config sm4;

	public String getActive() {
		return active;
	}

	public DefaultPrefix getEnumActive() {
		try {
			if (!StringUtils.hasText(active)) {
				return null;
			}
			return DefaultPrefix.valueOf(active.toUpperCase());
		}
		catch (IllegalArgumentException e) {
			LOGGER.warn("Unknown algorithm type: {}", active);
			return null;
		}
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Sm4Config getSm4() {
		return sm4;
	}

	public void setSm4(Sm4Config sm4) {
		this.sm4 = sm4;
	}

}
