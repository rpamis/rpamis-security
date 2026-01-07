package com.rpamis.security.starter.field.impl.desensitization;


import com.rpamis.security.starter.field.FieldProcess;
import com.rpamis.security.starter.field.ProcessContext;
import com.rpamis.security.starter.field.TypeHandler;

import java.util.List;

/**
 * 脱敏基础返回值处理器，用于所有带泛型的对象
 * 比如List<>,Map<>,Collections<>,自定义返回体
 *
 * @author benym
 * @date 2023/9/6 16:35
 */
public class MaskingResponseProcessor implements FieldProcess {

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
