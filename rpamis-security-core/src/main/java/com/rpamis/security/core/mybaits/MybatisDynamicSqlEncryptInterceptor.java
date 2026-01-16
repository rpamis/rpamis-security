package com.rpamis.security.core.mybaits;

import com.rpamis.security.core.utils.SecurityResolver;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Map;
import java.util.Objects;

/**
 * mybatis动态SQL处理插件
 * 用于处理List入参，foreach情况，需要在update或query最早的解析阶段进行对象处理
 * 如果采用ParameterHandler或ResultSetHandler时处理，此时动态SQL已经准备好了，无论再怎么加密对象
 * SQL中的动态参数依旧还是未加密的
 * 对于动态SQL的处理在
 * @see org.apache.ibatis.mapping.MappedStatement#getBoundSql(Object)
 * 执行阶段早于Executor调用
 * @see org.apache.ibatis.executor.statement.StatementHandler#parameterize(java.sql.Statement)
 * 处理时需要注意mybatis会将参数变为2个，比如int batchInsertWithList(@Param("testVersionDOList") List<TestVersionDO> testVersionDOList);
 * 此时的Param为testVersionDOList，但mybatis处理parameterObject时，还有默认添加param1为key，value为testVersionDOList的变量用于后续处理
 * 且此时param1的对象引用和testVersionDOList为key的对象相同
 * 注意此时对两个对象的引用保持相同，避免insert操作后拷贝id回源实体不成功
 * @see org.apache.ibatis.executor.statement.PreparedStatementHandler.update
 * 这里选择不进行深克隆，直接对对象进行脱敏，在后续的
 * @see MybatisEncryptInterceptor
 * 再进行深克隆，避免对源对象后置操作(save后再操作)等造成影响
 *
 * @author benym
 * @date 2023/9/11 22:30
 */

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
//        对于select请求不做处理，通常加密字段无法进行模糊查询，只能外部进行手动加密后进行等值查询，符合逻辑
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisDynamicSqlEncryptInterceptor implements Interceptor {

    private final SecurityResolver securityResolver;

    public MybatisDynamicSqlEncryptInterceptor(SecurityResolver securityResolver) {
        this.securityResolver = securityResolver;
    }

    @Override
    @SuppressWarnings("all")
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取拦截器拦截的设置参数对象DefaultParameterHandler
        final Object[] args = invocation.getArgs();
        Object parameterObject = args[1];
        if (Objects.isNull(parameterObject)) {
            return invocation.proceed();
        }
        // 如果为mybatis foreach用法，外部为List入参，内部统一处理为Map
        if (parameterObject instanceof Map) {
            Map<String, Object> parameterObjectMap = (Map<String, Object>) parameterObject;
            for (Map.Entry<String, Object> paramObjectEntry : parameterObjectMap.entrySet()) {
                Object value = paramObjectEntry.getValue();
                if (value != null) {
                    // 此处不能进行深拷贝复制，因为新增后id的回填需要上下文同一个对象引用
                    // 详情可见org.apache.ibatis.executor.statement.PreparedStatementHandler.update
                    // 进行加密
                    Object encryptObject = securityResolver.encryptFiled(value);
                    parameterObjectMap.put(paramObjectEntry.getKey(), encryptObject);
                }
            }
            invocation.getArgs()[1] = parameterObjectMap;
        }
        return invocation.proceed();
    }
}
