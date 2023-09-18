package com.rpamis.security.starter.field;

/**
 * 字段Field处理接口
 *
 * @author benym
 * @date 2023/9/6 14:18
 */
public interface FieldProcess {

    /**
     * 根据上下文进行field处理
     *
     * @param processContext 脱敏处理上下文
     * @throws IllegalAccessException
     */
    void processField(ProcessContext processContext) throws IllegalAccessException;
}
