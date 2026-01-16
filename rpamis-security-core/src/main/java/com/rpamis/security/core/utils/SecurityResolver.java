package com.rpamis.security.core.utils;

import com.rpamis.security.annotation.SecurityField;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.TypeHandler;
import com.rpamis.security.core.field.impl.ArrayTypeHandler;
import com.rpamis.security.core.field.impl.CollectionTypeHandler;
import com.rpamis.security.core.field.impl.MapTypeHandler;
import com.rpamis.security.core.field.impl.OtherTypeHandler;
import com.rpamis.security.core.field.impl.database.FinderSecurityProcessor;
import com.rpamis.security.core.field.impl.database.SecurityAnnotationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 加解密注解处理者
 *
 * @author benym
 * @since 2023/8/31 16:59
 */
public class SecurityResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityResolver.class);

    /**
     * 缓存Class->filed Map
     */
    private final ConcurrentHashMap<Class<?>, List<Field>> classToFieldMap = new ConcurrentHashMap<>();

    /**
     * 缓存Class->解密filed Map
     */
    private final ConcurrentHashMap<Class<?>, List<Field>> classToDecryptFieldMap = new ConcurrentHashMap<>();


    /**
     * 加解密工具类
     */
    private final SecurityUtils securityUtils;

    /**
     * 防重复加密Set
     */
    private static final Set<Object> REFERENCE_SET = Collections.newSetFromMap(new IdentityHashMap<>(128));

    /**
     * 防重复解密Set
     */
    private static final Set<Object> DECRYPT_REFERENCE_SET = Collections.newSetFromMap(new IdentityHashMap<>(128));

    /**
     * Filed处理者list
     */
    private static final List<FieldProcess> PROCESS_LIST = new ArrayList<>();

    /**
     * 类型处理者list
     */
    private static final List<TypeHandler> HANDLER_LIST = new ArrayList<>();

    public SecurityResolver(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
        // 必要处理者初始化
        PROCESS_LIST.add(new FinderSecurityProcessor());
        PROCESS_LIST.add(new SecurityAnnotationProcessor(securityUtils));
        HANDLER_LIST.add(new ArrayTypeHandler());
        HANDLER_LIST.add(new CollectionTypeHandler());
        HANDLER_LIST.add(new MapTypeHandler());
        HANDLER_LIST.add(new OtherTypeHandler());
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
        if (!REFERENCE_SET.contains(sourceParam)) {
            for (Field field : encryptFields) {
                ReflectionUtils.makeAccessible(field);
                Object sourceObject = ReflectionUtils.getField(field, sourceParam);
                // 目前只支持String
                if (!(sourceObject instanceof String)) {
                    continue;
                }
                String stringObject = String.valueOf(sourceObject);
                // 防止已经加密的字段重复加密
                // 如数据库已加密，但出库时加密字段实体未采用注解标识，则此时返回的是加密数据
                // 此加密数据又被赋值给另一个采用注解标识的实体字段，则会重复加密
                if (securityUtils.checkHasBeenEncrypted(stringObject)) {
                    continue;
                }
                ReflectionUtils.setField(field, sourceParam, securityUtils.encryptWithPrefix(stringObject));
                REFERENCE_SET.add(sourceParam);
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
        Deque<Object> analyzeDeque = new ArrayDeque<>();
        List<?> paramsList;
        if (params instanceof List) {
            paramsList = (List<?>) params;
        } else {
            paramsList = Collections.singletonList(params);
        }
        for (Object sourceParam : paramsList) {
            analyzeDeque.offer(sourceParam);
        }
        while (!analyzeDeque.isEmpty()) {
            Object currentObj = analyzeDeque.poll();
            List<Field> fields = classToDecryptFieldMap.computeIfAbsent(currentObj.getClass(), key -> getWillDecryptFields(currentObj));
            for (Field field : fields) {
                ReflectionUtils.makeAccessible(field);
                Object fieldValue = ReflectionUtils.getField(field, currentObj);
                if (null != fieldValue) {
                    Class<?> fieldValueClass = fieldValue.getClass();
                    ProcessContext processContext = new ProcessContext(currentObj, fieldValue, field,
                            fieldValueClass, DECRYPT_REFERENCE_SET, HANDLER_LIST, analyzeDeque);
                    // 字段解析并解密
                    for (FieldProcess fieldProcess : PROCESS_LIST) {
                        try {
                            fieldProcess.processField(processContext);
                        } catch (Exception e) {
                            LOGGER.error("SecurityResolver.decryptFiled error:", e);
                            throw new SecurityException(e);
                        }
                    }
                }
            }
        }
        return params;
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

    /**
     * 获取可能需要解密的字段，不含静态和final字段
     *
     * @param params params
     * @return List<Field>
     */
    private List<Field> getWillDecryptFields(Object params) {
        Field[] fields = FieldUtils.getAllFields(params);
        fields = Arrays.stream(fields).filter(field ->
                        !Modifier.isStatic(field.getModifiers())
                                && !Modifier.isFinal(field.getModifiers()))
                .collect(Collectors.toList()).toArray(new Field[0]);
        return Arrays.stream(fields).collect(Collectors.toList());
    }
}
