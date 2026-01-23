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
package com.rpamis.security.core.utils;

/**
 * 字符替换工具类
 *
 * @author benym
 * @since 2023/10/9 14:11
 */
public class StringReplaceUtils {

	private StringReplaceUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 字符串替换，首尾位置替换为symbol
	 * @param source source
	 * @param start start
	 * @param end end
	 * @param symbol symbol
	 * @return String
	 */
	public static String replace(String source, Integer start, Integer end, String symbol) {
		if (start >= 0 && end >= 0 && start < end && start < source.length()) {
			StringBuilder builder = new StringBuilder(source);
			for (int i = start; i <= end; i++) {
				builder.replace(i, i + 1, symbol);
			}
			return builder.toString();
		}
		return source;
	}

	/**
	 * 字符串替换，替换每个字符为symbol
	 * @param source source
	 * @param symbol symbol
	 * @return String
	 */
	public static String replaceEvery(String source, String symbol) {
		StringBuilder builder = new StringBuilder(source);
		for (int i = 0; i < source.length(); i++) {
			builder.replace(i, i + 1, symbol);
		}
		return builder.toString();
	}

}
