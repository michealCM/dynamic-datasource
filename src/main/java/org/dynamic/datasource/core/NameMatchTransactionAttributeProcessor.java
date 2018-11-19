package org.dynamic.datasource.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.dynamic.datasource.common.exception.DynamicDataSourceException;
import org.dynamic.datasource.core.context.NameMatchTransactionAttributeContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.ReflectionUtils;

/**
 * 读/写动态数据库选择处理器
 * 
 * 1.首先读取<tx:advice>事务属性配置,只记录写库操作函数通配字符
 * 
 * 2.对于所有读方法设置 read-only="true" 表示读取操作（以此来判断是选择读还是写库），其他操作都是走写库
 *    如<tx:method name="×××" read-only="true"/>
 *
 * 3.目前只适用于<tx:advice>情况 TODO 支持@Transactional注解事务
 * 
 * @author micheal
 * @date 2014-5-28 15:25:39 <BR/>
 */
public class NameMatchTransactionAttributeProcessor implements BeanPostProcessor {
	
	@Inject
	private NameMatchTransactionAttributeContext transactionManagerMethodContext;
	
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    	
        if(bean instanceof NameMatchTransactionAttributeSource) {
            try {
                NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource)bean;
                Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
                nameMapField.setAccessible(true);
                Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField.get(transactionAttributeSource);

                for(Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
                    RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute)entry.getValue();
                    String methodName = entry.getKey();

                    if(attr.isReadOnly()) {
                        continue;
                    }

                    attr.setPropagationBehavior(Propagation.REQUIRED.value());
                    transactionManagerMethodContext.addTransactionMethod(methodName);
                }

            } catch (Exception e) {
                throw new DynamicDataSourceException("process read/write transaction error", e.getMessage());
            }
        }

        return bean;
    }
    
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
}

