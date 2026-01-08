package com.rpamis.security.core.field;

/**
 * 类型处理器接口
 *
 * @author benym
 * @date 2023/9/6 16:59
 */
public interface TypeHandler {

    /**
     * 根据处理上下文进行类型处理
     *
     * @param processContext processContext
     * @return boolean
     */
    boolean handle(ProcessContext processContext);
}
