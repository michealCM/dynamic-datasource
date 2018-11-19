package org.dynamic.datasource.core.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dynamic.datasource.common.exception.DynamicDataSourceException;
import org.dynamic.datasource.common.util.AssertUtils;

/**
 * 自定义写库方法通配符，统一获取存储交给spring管理。方便之后再处理方法事物判断处理的时候获取 <BR/>
 * 
 * <p>
 * 
 * @author micheal
 * @date 2014-5-29 9:52:34 <BR/>
 */
public final class NameMatchTransactionAttributeContext implements Serializable{

	private static final long serialVersionUID = -2071117112287446010L;
	
	private List<String> transactionMethodList = new ArrayList<String>();
	
	public List<String> getTransactionMethodList() {
		AssertUtils.isEmpty(transactionMethodList,new DynamicDataSourceException("transactionMethodList is empty,no contains any tansaction method name!"));
		return transactionMethodList;
	}

	public void addTransactionMethod(String methodName){
		AssertUtils.isBlank(methodName,new DynamicDataSourceException("methodName not be null"));
        this.transactionMethodList.add(methodName);
	}

}
