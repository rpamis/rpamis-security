package com.rpamis.security.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rpamis.security.annotation.SecurityField;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName test_version
 */
@TableName(value = "test_version")
@Data
public class TestVersionDO implements Serializable {

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
	@SecurityField
	private String name;

	/**
	 * 身份证号
	 */
	@TableField(value = "id_card")
	@SecurityField
	private String idCard;

	/**
	 * 电话
	 */
	@TableField(value = "phone")
	@SecurityField
	private String phone;

	/**
	 * 版本号
	 */
	@TableField(value = "version")
	private Integer version;

}