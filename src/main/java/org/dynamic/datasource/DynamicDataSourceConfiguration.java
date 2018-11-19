package org.dynamic.datasource;

import org.dynamic.datasource.core.NameMatchTransactionAttributeProcessor;
import org.dynamic.datasource.core.context.NameMatchTransactionAttributeContext;
import org.dynamic.datasource.core.context.SwitchDataSourceContext;
import org.dynamic.datasource.core.interceptor.SwitchDataSourceInterceptor;
import org.dynamic.datasource.core.interceptor.SwitchDataSourcePointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * 动态数据源切换全注解bean的配置,初始化该模块的bean加入到spring容器中 <BR/>
 *
 * {@link Configuration}
 *
 * @author micheal
 * @version 1.0
 * @date 2014-8-20 16:10:34 <BR/>
 */
@Configuration
public class DynamicDataSourceConfiguration  {

    private String aspectJExpressionString;

    private String patternMatchClassPath;

    @Bean
    public NameMatchTransactionAttributeContext nameMatchTransactionAttributeContext(){
        return new NameMatchTransactionAttributeContext();
    }

    @Bean
    public NameMatchTransactionAttributeProcessor nameMatchTransactionAttributeProcessor(){
        return new NameMatchTransactionAttributeProcessor();
    }

    @Bean
    public SwitchDataSourceInterceptor switchDataSourceInterceptor(){
        return new SwitchDataSourceInterceptor();
    }

    @Bean
    public SwitchDataSourcePointcutAdvisor switchDataSourcePointcutAdvisor(){

        if(!StringUtils.isEmpty(patternMatchClassPath)){
            return new SwitchDataSourcePointcutAdvisor(switchDataSourceInterceptor(),patternMatchClassPath);
        }

        if(!StringUtils.isEmpty(aspectJExpressionString)){
            AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
            aspectJExpressionPointcut.setExpression(aspectJExpressionString);
            return new SwitchDataSourcePointcutAdvisor(switchDataSourceInterceptor(),aspectJExpressionPointcut);
        }

        return new SwitchDataSourcePointcutAdvisor(switchDataSourceInterceptor());
    }

    @Bean
    @Scope(value=WebApplicationContext.SCOPE_REQUEST,proxyMode=ScopedProxyMode.TARGET_CLASS)
    public SwitchDataSourceContext switchDataSourceContext(){
        return new SwitchDataSourceContext();
    }

    public void setAspectJExpressionString(String aspectJExpressionString) {
        this.aspectJExpressionString = aspectJExpressionString;
    }

    public void setPatternMatchClassPath(String patternMatchClassPath) {
        this.patternMatchClassPath = patternMatchClassPath;
    }
}
