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
package com.rpamis.security.test;

import com.rpamis.security.core.utils.SerializationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SerializationUtils 测试类
 *
 * @author benym
 * @since 2026/1/28
 */
public class SerializationUtilsTest {

	static class TestObject {

		private String name;

		private int age;

		private LocalDateTime createTime;

		public TestObject() {
		}

		public TestObject(String name, int age, LocalDateTime createTime) {
			this.name = name;
			this.age = age;
			this.createTime = createTime;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public LocalDateTime getCreateTime() {
			return createTime;
		}

		public void setCreateTime(LocalDateTime createTime) {
			this.createTime = createTime;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			TestObject that = (TestObject) o;

			if (age != that.age)
				return false;
			if (name != null ? !name.equals(that.name) : that.name != null)
				return false;
			// 宽松比较 LocalDateTime，只比较年月日时分秒
			if (createTime != null && that.createTime != null) {
				return createTime.truncatedTo(java.time.temporal.ChronoUnit.SECONDS)
					.equals(that.createTime.truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
			}
			return createTime == null && that.createTime == null;
		}

		@Override
		public int hashCode() {
			int result = name != null ? name.hashCode() : 0;
			result = 31 * result + age;
			result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
			return result;
		}

	}

	/**
	 * 测试普通对象的深克隆 验证克隆对象与原始对象相等但不是同一引用，且修改原始对象不影响克隆对象
	 */
	@Test
	@DisplayName("测试普通对象的深克隆")
	public void testDeepCloneWithPlainObject() {
		LocalDateTime now = LocalDateTime.now();
		TestObject original = new TestObject("test", 18, now);

		TestObject cloned = (TestObject) SerializationUtils.deepClone(original);

		assertNotNull(cloned);
		assertEquals(original, cloned);
		assertNotSame(original, cloned);

		// 修改原始对象，验证克隆对象不受影响
		original.setName("modified");
		assertEquals("test", cloned.getName());
	}

	/**
	 * 测试列表对象的深克隆 验证克隆列表与原始列表相等但不是同一引用，且修改原始列表中的对象不影响克隆列表
	 */
	@Test
	@DisplayName("测试列表对象的深克隆")
	public void testDeepCloneWithListObject() {
		LocalDateTime now = LocalDateTime.now();
		List<TestObject> originalList = new ArrayList<>();
		originalList.add(new TestObject("test1", 18, now));
		originalList.add(new TestObject("test2", 20, now));

		List<?> clonedList = (List<?>) SerializationUtils.deepClone(originalList);

		assertNotNull(clonedList);
		assertEquals(originalList.size(), clonedList.size());
		for (int i = 0; i < originalList.size(); i++) {
			TestObject original = originalList.get(i);
			TestObject cloned = (TestObject) clonedList.get(i);
			assertEquals(original, cloned);
			assertNotSame(original, cloned);
		}

		// 修改原始列表中的对象，验证克隆列表不受影响
		originalList.get(0).setName("modified");
		assertEquals("test1", ((TestObject) clonedList.get(0)).getName());
	}

	/**
	 * 测试不可序列化对象的深克隆 验证对不可序列化对象进行克隆时返回null
	 */
	@Test
	@DisplayName("测试不可序列化对象的深克隆")
	public void testDeepCloneWithException() {
		// 创建一个不可序列化的对象
		Object nonSerializable = new Object() {
			@Override
			public String toString() {
				return "Non-serializable object";
			}
		};

		Object result = SerializationUtils.deepClone(nonSerializable);
		assertNull(result);
	}

	/**
	 * 测试私有构造函数 验证通过反射调用私有构造函数会抛出异常
	 */
	@Test
	@DisplayName("测试私有构造函数")
	public void testPrivateConstructorThrowsException() {
		assertThrows(InvocationTargetException.class, () -> {
			// 使用反射创建实例
			java.lang.reflect.Constructor<SerializationUtils> constructor = SerializationUtils.class
				.getDeclaredConstructor();
			constructor.setAccessible(true);
			constructor.newInstance();
		});
	}

}
