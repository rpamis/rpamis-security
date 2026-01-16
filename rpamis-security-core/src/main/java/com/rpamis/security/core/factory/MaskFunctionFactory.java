package com.rpamis.security.core.factory;

import cn.hutool.core.util.DesensitizedUtil;
import com.rpamis.security.annotation.Masked;
import com.rpamis.security.core.utils.StringReplaceUtils;
import com.rpamis.security.mask.MaskType;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 脱敏函数工厂
 *
 * @author benym
 * @since 2023/10/8 17:28
 */
public class MaskFunctionFactory {

    private static final Map<MaskType, MaskFunction> MASK_FUNCTION_MAP = new ConcurrentHashMap<>(16);

    private static final String DEFAULT_MASK_SYMBOL = "*";

    public MaskFunctionFactory() {
        MASK_FUNCTION_MAP.put(MaskType.NO_MASK, this::noMask);
        MASK_FUNCTION_MAP.put(MaskType.NAME_MASK, this::nameMask);
        MASK_FUNCTION_MAP.put(MaskType.PHONE_MASK, this::phoneMask);
        MASK_FUNCTION_MAP.put(MaskType.IDCARD_MASK, this::idCardMask);
        MASK_FUNCTION_MAP.put(MaskType.EMAIL_MASK, this::emailMask);
        MASK_FUNCTION_MAP.put(MaskType.BANKCARD_MASK, this::bankCardMask);
        MASK_FUNCTION_MAP.put(MaskType.ADDRESS_MASK, this::addressMask);
        MASK_FUNCTION_MAP.put(MaskType.ALL_MASK, this::allMask);
        MASK_FUNCTION_MAP.put(MaskType.CUSTOM_MASK, this::customMask);
    }

    /**
     * 将未脱敏数据根据注解标识进行脱敏
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    public String maskData(String unMaskInput, Masked annotation) {
        MaskType type = annotation.type();
        MaskFunction maskFunction = MASK_FUNCTION_MAP.get(type);
        if (maskFunction != null) {
            return maskFunction.mask(unMaskInput, annotation);
        }
        return unMaskInput;
    }

    /**
     * 无需脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String noMask(String unMaskInput, Masked annotation) {
        return unMaskInput;
    }

    /**
     * 姓名脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String nameMask(String unMaskInput, Masked annotation) {
        String maskOnce = DesensitizedUtil.chineseName(unMaskInput);
        return maskOnce.replace(DEFAULT_MASK_SYMBOL, annotation.symbol());
    }

    /**
     * 电话脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String phoneMask(String unMaskInput, Masked annotation) {
        String maskOnce = DesensitizedUtil.mobilePhone(unMaskInput);
        return maskOnce.replace(DEFAULT_MASK_SYMBOL, annotation.symbol());
    }

    /**
     * 身份证脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String idCardMask(String unMaskInput, Masked annotation) {
        String maskOnce = DesensitizedUtil.idCardNum(unMaskInput, 5, 4);
        return maskOnce.replace(DEFAULT_MASK_SYMBOL, annotation.symbol());
    }

    /**
     * 邮件脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String emailMask(String unMaskInput, Masked annotation) {
        String maskOnce = DesensitizedUtil.email(unMaskInput);
        return maskOnce.replace(DEFAULT_MASK_SYMBOL, annotation.symbol());
    }

    /**
     * 银行卡脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String bankCardMask(String unMaskInput, Masked annotation) {
        String maskOnce = DesensitizedUtil.bankCard(unMaskInput);
        return maskOnce.replace(DEFAULT_MASK_SYMBOL, annotation.symbol());
    }

    /**
     * 地址脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String addressMask(String unMaskInput, Masked annotation) {
        String maskOnce = DesensitizedUtil.address(unMaskInput, unMaskInput.length() - 6);
        return maskOnce.replace(DEFAULT_MASK_SYMBOL, annotation.symbol());
    }

    /**
     * 全脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String allMask(String unMaskInput, Masked annotation) {
        if (StringUtils.hasLength(unMaskInput)) {
            return StringReplaceUtils.replaceEvery(unMaskInput, annotation.symbol());
        }
        return unMaskInput;
    }

    /**
     * 自定义脱敏类型
     *
     * @param unMaskInput unMaskInput
     * @param annotation  annotation
     * @return String
     */
    private String customMask(String unMaskInput, Masked annotation) {
        if (StringUtils.hasLength(unMaskInput)) {
            int start = annotation.start();
            int end = annotation.end();
            return StringReplaceUtils.replace(unMaskInput, start, end, annotation.symbol());
        }
        return unMaskInput;
    }
}
