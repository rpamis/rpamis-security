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
