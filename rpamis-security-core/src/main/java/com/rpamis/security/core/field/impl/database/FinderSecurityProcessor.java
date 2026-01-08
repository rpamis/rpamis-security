package com.rpamis.security.core.field.impl.database;

import com.rpamis.security.starter.field.FieldProcess;
import com.rpamis.security.starter.field.ProcessContext;
import com.rpamis.security.starter.field.TypeHandler;

import java.util.List;

/**
 * 寻找所有可解析字段类型的处理器
 *
 * @author benym
 * @date 2026/1/7 18:13
 */
public class FinderSecurityProcessor implements FieldProcess {

    @Override
    public void processField(ProcessContext processContext) throws IllegalAccessException {
        List<TypeHandler> handlerList = processContext.getHandlerList();
        for (TypeHandler handler : handlerList) {
            boolean handleResult = handler.handle(processContext);
            if (handleResult) {
                break;
            }
        }
    }
}
