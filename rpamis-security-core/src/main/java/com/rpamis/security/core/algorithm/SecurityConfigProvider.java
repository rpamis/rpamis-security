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
 * 安全配置提供者
 *
 * @author benym
 * @date 2025/9/11 18:18
 */
public interface SecurityConfigProvider {

	/**
	 * 获取sm4密钥
	 * @return String
	 */
	String getSm4key();

	/**
	 * 获取是否忽略解密失败
	 * @return Boolean
	 */
	Boolean getIgnoreDecryptFailed();

}
