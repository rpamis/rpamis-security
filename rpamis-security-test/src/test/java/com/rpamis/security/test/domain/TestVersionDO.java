package com.rpamis.security.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rpamis.security.annotation.SecurityField;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName test_version
 */
@TableName(value ="test_version")
@Data
public class TestVersionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "name")
    @SecurityField
    private String name;

    /**
     *
     */
    @TableField(value = "id_card")
    @SecurityField
    private String idCard;

    /**
     *
     */
    @TableField(value = "phone")
    @SecurityField
    private String phone;

    /**
     *
     */
    @TableField(value = "version")
    private Integer version;

}