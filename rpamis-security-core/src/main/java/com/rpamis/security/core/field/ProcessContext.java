package com.rpamis.security.core.field;


import com.rpamis.security.core.factory.MaskFunctionFactory;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * 处理上下文
 *
 * @author benym
 * @date 2023/9/6 16:28
 */
public class ProcessContext {

    /**
     * 当前处理的对象，用于脱敏后反射赋值回去
     */
    private Object currentObject;

    /**
     * 当前处理的字段的值
     */
    private Object fieldValue;

    /**
     * 当前处理的字段的Field
     */
    private Field field;

    /**
     * 当前处理的字段的Field Class
     */
    private Class<?> fieldValueClass;

    /**
     * 防重Set
     */
    private Set<Object> referenceSet;

    /**
     * 类型处理者列表
     */
    private List<TypeHandler> handlerList;

    /**
     * 解析队列
     */
    private Deque<Object> analyzeDeque;

    /**
     * 脱敏工厂
     */
    private MaskFunctionFactory maskFunctionFactory;

    public ProcessContext() {
    }

    public ProcessContext(Object currentObject, Object fieldValue, Field field, Class<?> fieldValueClass, Set<Object> referenceSet, List<TypeHandler> handlerList, Deque<Object> analyzeDeque) {
        this.currentObject = currentObject;
        this.fieldValue = fieldValue;
        this.field = field;
        this.fieldValueClass = fieldValueClass;
        this.referenceSet = referenceSet;
        this.handlerList = handlerList;
        this.analyzeDeque = analyzeDeque;
    }

    public Object getCurrentObject() {
        return currentObject;
    }

    public void setCurrentObject(Object currentObject) {
        this.currentObject = currentObject;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getFieldValueClass() {
        return fieldValueClass;
    }

    public void setFieldValueClass(Class<?> fieldValueClass) {
        this.fieldValueClass = fieldValueClass;
    }

    public Set<Object> getReferenceSet() {
        return referenceSet;
    }

    public void setReferenceSet(Set<Object> referenceSet) {
        this.referenceSet = referenceSet;
    }

    public Deque<Object> getAnalyzeDeque() {
        return analyzeDeque;
    }

    public void setAnalyzeDeque(Deque<Object> analyzeDeque) {
        this.analyzeDeque = analyzeDeque;
    }

    public List<TypeHandler> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<TypeHandler> handlerList) {
        this.handlerList = handlerList;
    }

    public MaskFunctionFactory getMaskFunctionFactory() {
        return maskFunctionFactory;
    }

    public void setMaskFunctionFactory(MaskFunctionFactory maskFunctionFactory) {
        this.maskFunctionFactory = maskFunctionFactory;
    }
}
