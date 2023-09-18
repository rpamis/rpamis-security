package com.rpamis.security.starter.autoconfigure;

import com.rpamis.security.starter.algorithm.SecurityAlgorithm;
import com.rpamis.security.starter.algorithm.impl.Sm4SecurityAlgorithmImpl;
import com.rpamis.security.starter.aop.DesensitizationAdvisor;
import com.rpamis.security.starter.aop.DesensitizationAspect;
import com.rpamis.security.starter.aop.DesensitizationInterceptor;
import com.rpamis.security.starter.mybaits.MybatisDecryptInterceptor;
import com.rpamis.security.starter.mybaits.MybatisDynamicSqlEncryptInterceptor;
import com.rpamis.security.starter.mybaits.MybatisEncryptInterceptor;
import com.rpamis.security.starter.utils.SecurityResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * 安全组件自动配置类
 *
 * @author benym
 * @date 2023/9/4 14:20
 */
@Configuration
@EnableConfigurationProperties(value = SecurityProperties.class)
@ConditionalOnProperty(prefix = SecurityProperties.PREFIX, value = "enable", havingValue = "true")
public class SecurityAutoConfiguration {

    /**
     * Sm4国密算法实现类，仅当算法类型为sm4时注入
     *
     * @param securityProperties securityProperties
     * @return SecurityAlgorithm
     */
    @Bean
    @ConditionalOnExpression("'${rpamis.security.algorithm}'.equals('sm4')")
    @ConditionalOnMissingBean
    SecurityAlgorithm sm4SecurityAlgorithm(SecurityProperties securityProperties) {
        if (!StringUtils.hasLength(securityProperties.getSm4key())) {
            throw new IllegalArgumentException("sm4 encryption key is null, please set");
        }
        return new Sm4SecurityAlgorithmImpl(securityProperties);
    }

    /**
     * 默认安全算法，直接返回原值
     *
     * @return SecurityAlgorithm
     */
    @Bean
    @ConditionalOnMissingBean
    SecurityAlgorithm defaultSecurityAlgorithm(){
        return new SecurityAlgorithm() {
            @Override
            public String encrypt(String sourceValue) {
                return sourceValue;
            }

            @Override
            public String decrypt(String sourceValue) {
                return sourceValue;
            }
        };
    }

    /**
     * 加解密处理者
     *
     * @param securityAlgorithm 安全算法
     * @return SecurityResolver
     */
    @Bean
    @Order(1)
    SecurityResolver securityResolver(SecurityAlgorithm securityAlgorithm) {
        return new SecurityResolver(securityAlgorithm);
    }

    /**
     * 动态SQL加密插件，用于处理动态foreach形态拼接
     *
     * @param securityResolver securityResolver
     * @return MybatisDynamicSqlEncryptInterceptor
     */
    @Bean
    MybatisDynamicSqlEncryptInterceptor mybatisDynamicSqlEncryptInterceptor(SecurityResolver securityResolver) {
        return new MybatisDynamicSqlEncryptInterceptor(securityResolver);
    }

    /**
     * 加密插件
     *
     * @param securityResolver SecurityResolver
     * @return MybatisEncryptInterceptor
     */
    @Bean
    MybatisEncryptInterceptor mybatisEncryptInterceptor(SecurityResolver securityResolver) {
        return new MybatisEncryptInterceptor(securityResolver);
    }

    /**
     * 解密插件
     *
     * @param securityResolver SecurityResolver
     * @return MybatisDecryptInterceptor
     */
    @Bean
    MybatisDecryptInterceptor mybatisDecryptInterceptor(SecurityResolver securityResolver) {
        return new MybatisDecryptInterceptor(securityResolver);
    }

    /**
     * DesensitizationAspect切面类仅当脱敏切面开启时注入
     *
     * @return DesensitizationAspect
     */
    @Bean
    @ConditionalOnExpression("${rpamis.security.desensitization-enable:true}")
    DesensitizationAspect desensitizationAspect(){
        return new DesensitizationAspect();
    }

    /**
     * DesensitizationInterceptor动态代理类仅当脱敏切面开启时注入
     *
     * @return DesensitizationInterceptor
     */
    @Bean
    @ConditionalOnExpression("${rpamis.security.desensitization-enable:true}")
    DesensitizationInterceptor desensitizationInterceptor(DesensitizationAspect desensitizationAspect){
        return new DesensitizationInterceptor(desensitizationAspect);
    }

    /**
     * DesensitizationAdvisor仅当脱敏切面开启时注入
     *
     * @return DesensitizationAdvisor
     */
    @Bean
    @ConditionalOnExpression("${rpamis.security.desensitization-enable:true}")
    DesensitizationAdvisor desensitizationAdvisor(DesensitizationInterceptor desensitizationInterceptor) {
        return new DesensitizationAdvisor(desensitizationInterceptor);
    }
}
