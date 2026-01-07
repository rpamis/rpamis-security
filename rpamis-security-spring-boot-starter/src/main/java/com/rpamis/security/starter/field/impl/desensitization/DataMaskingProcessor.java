package com.rpamis.security.starter.field.impl.desensitization;

import com.rpamis.security.annotation.Masked;
import com.rpamis.security.starter.factory.MaskFunctionFactory;
import com.rpamis.security.starter.field.FieldProcess;
import com.rpamis.security.starter.field.ProcessContext;

import java.lang.reflect.Field;

/**
 * DataMasking脱敏注解处理
 * 只能处理实体为非泛型的，实体内可带有其余泛型
 * 比如DemoUser可以处理, DemoUser内的List<DemoUser>也可以处理
 * 但对于直接的List<DemoUser>无法处理，因为无法知道泛型内对象到底是什么，取哪个字段
 * 对于这样的泛型情况统一由MaskingResponseProcessor处理
 * @see MaskingResponseProcessor
 *
 * @author benym
 * @date 2023/9/6 14:30
 */
public class DataMaskingProcessor implements FieldProcess {

    @Override
    @SuppressWarnings("all")
    public void processField(ProcessContext processContext) throws IllegalAccessException {
        Field field = processContext.getField();
        Object fieldValue = processContext.getFieldValue();
        Object currentObject = processContext.getCurrentObject();
        if (field.isAnnotationPresent(Masked.class)) {
            Masked annotation = field.getAnnotation(Masked.class);
            MaskFunctionFactory maskFunctionFactory = processContext.getMaskFunctionFactory();
            String maskValue = maskFunctionFactory.maskData(String.valueOf(fieldValue), annotation);
            field.set(currentObject, maskValue);
        }
    }
}
