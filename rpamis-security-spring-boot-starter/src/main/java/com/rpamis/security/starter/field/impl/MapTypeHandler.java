package com.rpamis.security.starter.field.impl;

import com.rpamis.security.starter.field.ProcessContext;
import com.rpamis.security.starter.field.TypeHandler;
import com.rpamis.security.starter.utils.MaskAnnotationResolver;

import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

/**
 * 处理Map数据类型
 *
 * @author benym
 * @date 2023/9/6 17:01
 */
public class MapTypeHandler implements TypeHandler {
    @Override
    public boolean handle(ProcessContext processContext) {
        Object fieldValue = processContext.getFieldValue();
        Set<Integer> referenceSet = processContext.getReferenceSet();
        Deque<Object> analyzeDeque = processContext.getAnalyzeDeque();
        if (fieldValue instanceof Map<?, ?>) {
            Map<?, ?> fieldValueMap = (Map<?, ?>) fieldValue;
            Collection<?> mapValues = fieldValueMap.values();
            for (Object value : mapValues) {
                if (MaskAnnotationResolver.isNotBaseType(value.getClass(), value, referenceSet)) {
                    analyzeDeque.offer(value);
                }
            }
            return true;
        }
        return false;
    }
}
