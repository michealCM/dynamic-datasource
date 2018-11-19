package org.dynamic.datasource.core.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.dynamic.datasource.common.exception.DynamicDataSourceException;
import org.dynamic.datasource.core.DynamicDataSource;
import org.dynamic.datasource.core.annotation.DataSourceEnumType;
import org.dynamic.datasource.core.annotation.DataSourceSwitch;
import org.dynamic.datasource.core.context.NameMatchTransactionAttributeContext;
import org.dynamic.datasource.core.context.SwitchDataSourceContext;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.PatternMatchUtils;

/**
 * 
 * 主从数据库切换选择通知器 
 * {@link MethodInterceptor}
 * 
 * @author micheal
 * @version 1.0
 * @date 2014-8-20 10:44:14 <BR/>
 */
public class SwitchDataSourceInterceptor implements MethodInterceptor{
	
	@Inject
	private SwitchDataSourceContext switchDataSourceContext;
	@Inject
	private DynamicDataSource dynamicDataSource;
	@Inject
	private NameMatchTransactionAttributeContext transactionManagerMethodContext;
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if(null ==  switchDataSourceContext.getSwitchDataSource()){
			ProxyMethodInvocation proxyMethodInvocation = (ProxyMethodInvocation) methodInvocation;
			if(!AopUtils.isAopProxy(proxyMethodInvocation.getProxy())){
				throw new DynamicDataSourceException("methodInvocation is not aop proxy class");
			}
			 
			final Class<?> targetClass = methodInvocation.getThis().getClass();
			final DataSourceSwitch classSwitch = targetClass.getAnnotation(DataSourceSwitch.class);
			final Transactional classTransactional = targetClass.getAnnotation(Transactional.class);
			
			//判断代理类型（jdk还是cglib）
			 Method targetMethod = methodInvocation.getMethod();
			if(AopUtils.isJdkDynamicProxy(proxyMethodInvocation.getProxy())){
				targetMethod = targetClass.getMethod(targetMethod.getName(),targetMethod.getParameterTypes());
			}
			final Transactional transactional = targetMethod.getAnnotation(Transactional.class);
			final DataSourceSwitch methodSwitch = targetMethod.getAnnotation(DataSourceSwitch.class);
			
			if (null != transactional || classTransactional != null) {
				switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getWriteSwitchDataSource());
			} else if(null != methodSwitch || classSwitch != null ) {
				DataSourceEnumType switchType;
				if (null != methodSwitch) {
					switchType = methodSwitch.value();
				} else {
					switchType = classSwitch.value();
				}
				switch (switchType) {
				case MASTER_WRITE:
					switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getWriteSwitchDataSource());
					break;

				case SLAVE_READ:
					switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getReadSwitchDataSource());
					break;

				default:
					switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getReadSwitchDataSource());
					break;
				}
			}else{
				String bestNameMatch = null;
				if(transactionManagerMethodContext.getTransactionMethodList().size() > 0){
					for (String mappedName : transactionManagerMethodContext.getTransactionMethodList()) {
						if (PatternMatchUtils.simpleMatch(targetMethod.getName(), mappedName)) {
							bestNameMatch = mappedName;
							break;
						}
					}
				}
				
				if(null == bestNameMatch){
					switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getReadSwitchDataSource());
				}else{
					switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getWriteSwitchDataSource());
				}
			}
		}else if(switchDataSourceContext.getSwitchDataSource().getTypeName().equals(
				DataSourceEnumType.SLAVE_READ.getDataSourceType())){
			switchDataSourceContext.setSwitchDataSource(dynamicDataSource.getReadSwitchDataSource());
		}
		
		return methodInvocation.proceed();
	}

}
