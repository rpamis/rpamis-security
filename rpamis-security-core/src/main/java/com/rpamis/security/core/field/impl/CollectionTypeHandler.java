package com.rpamis.security.core.field.impl;

import com.rpamis.security.starter.field.ProcessContext;
import com.rpamis.security.starter.field.TypeHandler;
import com.rpamis.security.starter.utils.MaskAnnotationResolver;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Deque;
import java.util.Set;

/**
 * 处理Collection数据类型
 *
 * @author benym
 * @date 2023/9/6 17:00
 */
public class CollectionTypeHandler implements TypeHandler {

    @Override
    public boolean handle(ProcessContext processContext) {
        Object fieldValue = processContext.getFieldValue();
        Set<Integer> referenceSet = processContext.getReferenceSet();
        Deque<Object> analyzeDeque = processContext.getAnalyzeDeque();
        if (fieldValue instanceof Collection<?>) {
            Collection<?> fieldValueList = (Collection<?>) fieldValue;
            if (CollectionUtils.isEmpty(fieldValueList)) {
                return false;
            }
            for (Object collectionObject : fieldValueList) {
                if (MaskAnnotationResolver.isNotBaseType(collectionObject.getClass(), collectionObject, referenceSet)) {
                    analyzeDeque.offer(collectionObject);
                }
            }
            return true;
        }
        return false;
    }
}
