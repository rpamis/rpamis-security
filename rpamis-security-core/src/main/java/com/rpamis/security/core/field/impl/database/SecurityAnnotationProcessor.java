package com.rpamis.security.core.field.impl.database;

import com.rpamis.security.annotation.SecurityField;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.utils.SecurityUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * SecurityField注解解密处理
 *
 * @author benym
 * @date 2026/1/7 18:14
 */
public class SecurityAnnotationProcessor implements FieldProcess {

    private final SecurityUtils securityUtils;

    public SecurityAnnotationProcessor(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public void processField(ProcessContext processContext) throws IllegalAccessException {
        Field field = processContext.getField();
        Object fieldValue = processContext.getFieldValue();
        Object currentObject = processContext.getCurrentObject();
        if (field.isAnnotationPresent(SecurityField.class) && fieldValue instanceof String) {
            String stringObject = String.valueOf(fieldValue);
            String decryptedString = securityUtils.decryptWithPrefix(stringObject);
            ReflectionUtils.setField(field, currentObject, decryptedString);
        }
    }
}
