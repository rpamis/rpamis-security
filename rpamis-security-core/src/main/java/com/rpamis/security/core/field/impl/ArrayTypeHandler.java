package com.rpamis.security.core.field.impl;

import com.rpamis.security.starter.field.ProcessContext;
import com.rpamis.security.starter.field.TypeHandler;
import com.rpamis.security.starter.utils.MaskAnnotationResolver;

import java.lang.reflect.Array;
import java.util.Deque;
import java.util.Set;

/**
 * 处理Array数据类型
 *
 * @author benym
 * @date 2023/9/6 17:01
 */
public class ArrayTypeHandler implements TypeHandler {

    @Override
    public boolean handle(ProcessContext processContext) {
        Class<?> fieldValueClass = processContext.getFieldValueClass();
        Object fieldValue = processContext.getFieldValue();
        Set<Integer> referenceSet = processContext.getReferenceSet();
        Deque<Object> analyzeDeque = processContext.getAnalyzeDeque();
        if (fieldValueClass.isArray()) {
            int length = Array.getLength(fieldValue);
            for (int i = 0; i < length; i++) {
                Object arrayObject = Array.get(fieldValue, i);
                if (null != arrayObject && MaskAnnotationResolver.isNotBaseType(arrayObject.getClass(), arrayObject, referenceSet)) {
                    analyzeDeque.offer(arrayObject);
                }
            }
            return true;
        }
        return false;
    }
}
