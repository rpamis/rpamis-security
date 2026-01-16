package com.rpamis.security.core.mybaits;

import com.rpamis.security.core.mybatisplus.Constants;
import com.rpamis.security.core.mybatisplus.PluginUtils;
import com.rpamis.security.core.utils.SecurityResolver;
import com.rpamis.security.core.utils.SerializationUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Objects;

/**
 * Mybatis加密拦截器，入库时加密，查询时加密做等值查询
 * 同时兼容mybatis plus
 * 区别于
 * @see MybatisDynamicSqlEncryptInterceptor
 * 这个插件的在其后执行，在正确修改动态sql加密后，对对象进行深度克隆
 * 避免save或update之后还需要对对象进行操作，但对象已经被加密的问题
 * 同时处理非foreach等动态sql的克隆加密
 *
 * @author benym
 * @since 2023/8/31 16:17
 */
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters",
                args = PreparedStatement.class)
})
public class MybatisEncryptInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisEncryptInterceptor.class);

    private final SecurityResolver securityResolver;

    public MybatisEncryptInterceptor(SecurityResolver securityResolver) {
        this.securityResolver = securityResolver;
    }

    @Override
    @SuppressWarnings("all")
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            if (!(invocation.getTarget() instanceof ParameterHandler)) {
                return invocation.proceed();
            }
            ParameterHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            // 如果是select操作，或者mybatis-plus的@SqlParser(filter = true)跳过该方法解析，不进行验证
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
            if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
                return invocation.proceed();
            }
            ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
            Object parameterObject = parameterHandler.getParameterObject();
            if (Objects.isNull(parameterObject)) {
                return invocation.proceed();
            }
            // mybatis对于List类型的处理，底层默认为Map类型
            if (parameterObject instanceof Map) {
                Map<String, Object> parameterObjectMap = (Map<String, Object>) parameterObject;
                // 如果不包含mybatis-plus的实体key
                if (parameterObjectMap.containsKey(Constants.ENTITY)) {
                    Object entity = parameterObjectMap.get(Constants.ENTITY);
                    if (entity != null) {
                        // 深拷贝复制
                        Object deepCloneEntity = SerializationUtils.deepClone(entity);
                        if (Objects.nonNull(deepCloneEntity)) {
                            // 进行加密
                            Object encryptObject = securityResolver.encryptFiled(deepCloneEntity);
                            parameterObjectMap.put(Constants.ENTITY, encryptObject);
                        }
                    }
                }
            } else {
                // mybatis处理
                Object deepCloneEntity = SerializationUtils.deepClone(parameterObject);
                if (Objects.nonNull(deepCloneEntity)) {
                    Field field = parameterHandler.getClass().getDeclaredField("parameterObject");
                    field.setAccessible(true);
                    field.set(parameterHandler, deepCloneEntity);
                    // 进行加密
                    Object encryptObject = securityResolver.encryptFiled(deepCloneEntity);
                }
            }
            return invocation.proceed();
        } catch (IllegalAccessException | InvocationTargetException e) {
            // 如果是invocation.proceed()过程执行真实方法异常了，则直接抛出，非security插件问题
            throw e;
        } catch (Exception e) {
            LOGGER.error("encrypt process: rpamis security mybatis encrypt interceptor unknown exception, the original value is returned, and no impact is caused", e);
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
