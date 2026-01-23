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
package com.rpamis.security.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpamis.security.test.domain.TestVersionV2DO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TestVersionV2Mapper
 *
 * @author benym
 * @since 2025/9/10 18:08
 */
@Mapper
public interface TestVersionV2Mapper extends BaseMapper<TestVersionV2DO> {

}
