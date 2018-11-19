package org.dynamic.datasource.core.context;

import java.io.Serializable;

/**
 * 
 * 数据源选择会话
 * 
 * @author micheal
 * @date 2014-8-21 13:02:15 <BR/>
 */
public final class SwitchDataSourceContext implements Serializable{
	
	private static final long serialVersionUID = 1296043861024360727L;

	private ThreadLocal<SwitchDataSource> switchDataSourceThreadLocal = new ThreadLocal<SwitchDataSource>();

	public SwitchDataSource getSwitchDataSource(){
		return this.switchDataSourceThreadLocal.get();
	}

	public void setSwitchDataSource(SwitchDataSource switchDataSource){
		if(null == this.getSwitchDataSource()){
			this.switchDataSourceThreadLocal.set(switchDataSource);
		}
	}
}
