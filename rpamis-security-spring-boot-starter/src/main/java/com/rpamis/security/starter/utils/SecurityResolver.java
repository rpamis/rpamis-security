package com.rpamis.security.starter.utils;

import com.rpamis.security.annotation.SecurityField;
import com.rpamis.security.starter.algorithm.SecurityAlgorithm;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 加解密注解处理者
 *
 * @author benym
 * @date 2023/8/31 16:59
 */
public class SecurityResolver {

    /**
     * 缓存Class->filed Map
     */
    private final ConcurrentHashMap<Class<?>, List<Field>> classToFieldMap = new ConcurrentHashMap<>();

    /**
     * 加密算法
     */
    private final SecurityAlgorithm securityAlgorithm;

    /**
     * 防重复加密Set
     */
    private static final Set<Integer> REFERENCE_SET = new HashSet<>(128);

    public SecurityResolver(SecurityAlgorithm securityAlgorithm) {
        this.securityAlgorithm = securityAlgorithm;
    }

    /**
     * 对字段加密
     *
     * @param deepCloneParam 源拷贝对象
     */
    public Object encryptFiled(Object deepCloneParam) {
        if (Objects.isNull(deepCloneParam)) {
            return null;
        }
        if (deepCloneParam instanceof List) {
            List<?> paramsList = (List<?>) deepCloneParam;
            if (!CollectionUtils.isEmpty(paramsList)) {
                Object deepCloneSingleObject = paramsList.get(0);
                Class<?> paramsClass = paramsList.get(0).getClass();
                List<Field> fields = classToFieldMap.computeIfAbsent(paramsClass, key -> getParamsFields(deepCloneSingleObject));
                return processEncryptList(paramsList, fields);
            }
        } else {
            Class<?> paramsClass = deepCloneParam.getClass();
            List<Field> fields = classToFieldMap.computeIfAbsent(paramsClass, key -> getParamsFields(deepCloneParam));
            return processEncrypt(deepCloneParam, fields);
        }
        return deepCloneParam;
    }

    /**
     * 处理加密-单一实体
     *
     * @param sourceParam   源对象
     * @param encryptFields 需要加密字段集合
     * @return Object
     */
    private Object processEncrypt(Object sourceParam, List<Field> encryptFields) {
        if (!REFERENCE_SET.contains(sourceParam.hashCode())) {
            for (Field field : encryptFields) {
                ReflectionUtils.makeAccessible(field);
                Object sourceObject = ReflectionUtils.getField(field, sourceParam);
                // 目前只支持String
                if (!(sourceObject instanceof String)) {
                    continue;
                }
                String encryptedString = securityAlgorithm.encrypt(String.valueOf(sourceObject));
                ReflectionUtils.setField(field, sourceParam, encryptedString);
                REFERENCE_SET.add(sourceParam.hashCode());
            }
        }
        return sourceParam;
    }

    /**
     * 处理加密-List
     *
     * @param sourceList    源对象List
     * @param encryptFields 需要加密字段集合
     */
    private Object processEncryptList(List<?> sourceList, List<Field> encryptFields) {
        for (Object sourceParam : sourceList) {
            processEncrypt(sourceParam, encryptFields);
        }
        return sourceList;
    }

    /**
     * 对字段进行解密
     *
     * @param params params
     * @return Object
     */
    public Object decryptFiled(Object params) {
        if (Objects.isNull(params)) {
            return null;
        }
        // 此处没有并发问题，只是为了通过stream的lambda编译
        AtomicReference<Class<?>> clazz = new AtomicReference<>();
        AtomicReference<Object> object = new AtomicReference<>();
        // 解析返回值为List的
        if (params instanceof List) {
            List<?> sourceList = (List<?>) params;
            if (CollectionUtils.isEmpty(sourceList)) {
                return params;
            }
            sourceList.stream().filter(Objects::nonNull)
                    .findFirst()
                    .ifPresent(source -> {
                        clazz.set(source.getClass());
                        object.set(source);
                    });
        } else {
            clazz.set(params.getClass());
            object.set(params);
        }
        if (null == clazz.get()) {
            return params;
        }
        List<Field> decryptFields = getParamsFields(object.get());
        if (!CollectionUtils.isEmpty(decryptFields)) {
            List<?> paramsList;
            if (params instanceof List) {
                paramsList = (List<?>) params;
            } else {
                paramsList = Collections.singletonList(params);
            }
            return processDecrypt(paramsList, decryptFields);
        }
        return params;
    }

    /**
     * 处理解密
     *
     * @param sourceList    源对象List
     * @param decryptFields 需要解密字段集合
     * @return List<?>
     */
    private List<?> processDecrypt(List<?> sourceList, List<Field> decryptFields) {
        for (Object sourceParam : sourceList) {
            for (Field field : decryptFields) {
                ReflectionUtils.makeAccessible(field);
                Object sourceObject = ReflectionUtils.getField(field, sourceParam);
                if (sourceObject instanceof String) {
                    String newStr = securityAlgorithm.decrypt(String.valueOf(sourceObject));
                    ReflectionUtils.setField(field, sourceParam, newStr);
                }
            }
        }
        return sourceList;
    }

    /**
     * 获取原始实体内所有被SecurityField标记的Field
     *
     * @param params SecurityField
     * @return List<Field>
     */
    private List<Field> getParamsFields(Object params) {
        return Arrays.stream(FieldUtils.getAllFields(params))
                .filter(field -> Objects.nonNull(field.getAnnotation(SecurityField.class)))
                .collect(Collectors.toList());
    }
}
