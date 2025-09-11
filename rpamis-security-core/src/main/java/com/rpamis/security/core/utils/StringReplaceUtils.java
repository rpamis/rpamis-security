package com.rpamis.security.core.utils;

/**
 * 字符替换工具类
 *
 * @author benym
 * @date 2023/10/9 14:11
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
