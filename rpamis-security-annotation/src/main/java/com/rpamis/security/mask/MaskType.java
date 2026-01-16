package com.rpamis.security.mask;

/**
 * 脱敏类型函数
 *
 * @author benym
 * @since 2023/10/8 16:53
 */
public enum MaskType {

    /**
     * 不脱敏
     */
    NO_MASK,
    /**
     * 姓名脱敏
     */
    NAME_MASK,
    /**
     * 电话脱敏
     */
    PHONE_MASK,
    /**
     * 身份证脱敏
     */
    IDCARD_MASK,
    /**
     * 邮箱脱敏
     */
    EMAIL_MASK,
    /**
     * 银行卡脱敏
     */
    BANKCARD_MASK,
    /**
     * 地址脱敏
     */
    ADDRESS_MASK,
    /**
     * 全脱敏
     */
    ALL_MASK,
    /**
     * 自定义脱敏
     */
    CUSTOM_MASK
}
