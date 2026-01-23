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
package com.rpamis.security.core.algorithm;

/**
 * 加解密算法接口
 *
 * @author benym
 * @since 2023/8/31 17:14
 */
public interface SecurityAlgorithm {

	/**
	 * 加密原始字段
	 * @param sourceValue 原始加密字段
	 * @return String
	 */
	String encrypt(String sourceValue);

	/**
	 * 解密已加密字段
	 * @param sourceValue 原始已加密字段
	 * @return String
	 */
	String decrypt(String sourceValue);

}
