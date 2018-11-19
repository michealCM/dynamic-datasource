package org.dynamic.datasource.core.interceptor;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 *
 * 根据包名称路径进行拦截处理时，由于事务在service层进行，所以对service.impl进行拦截 <BR/>
 *
 * {@link DefaultPointcutAdvisor}
 *
 * @author micheal
 * @version 1.0 <BR/>
 * @date 2014-8-20 12:07:32 <BR/>
 */
public class SwitchDataSourcePointcutAdvisor extends DefaultPointcutAdvisor{

    private static final long serialVersionUID = -5855719617370187969L;

    //默认对类路径进行拦截
    private static final String DEFAULT_PATTERN_CLASS_PATH = "*.service.impl.*";

    public SwitchDataSourcePointcutAdvisor(SwitchDataSourceInterceptor switchDataSourceInterceptor){
        super(new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return PatternMatchUtils.simpleMatch(DEFAULT_PATTERN_CLASS_PATH,targetClass.getName());
            }
        }, switchDataSourceInterceptor);
    }

    public SwitchDataSourcePointcutAdvisor(SwitchDataSourceInterceptor switchDataSourceInterceptor,final String patternMatchClassPath){
        super(new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return PatternMatchUtils.simpleMatch(patternMatchClassPath,targetClass.getName());
            }
        }, switchDataSourceInterceptor);
    }

    public SwitchDataSourcePointcutAdvisor(SwitchDataSourceInterceptor switchDataSourceInterceptor,
                                           AspectJExpressionPointcut aspectJExpressionPointcut){
        super(aspectJExpressionPointcut, switchDataSourceInterceptor);
    }

}