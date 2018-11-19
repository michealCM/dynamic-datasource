package org.dynamic.datasource.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.dynamic.datasource.common.exception.DynamicDataSourceException;
import org.dynamic.datasource.common.util.AssertUtils;
import org.dynamic.datasource.core.annotation.DataSourceEnumType;
import org.dynamic.datasource.core.context.SwitchDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.CollectionUtils;

/**
 * 
 * 初始化主从读写库 
 * 
 * @author micheal
 * @version 1.0
 * @date 2014-8-20 10:17:38 <BR/>
 */
public abstract class AbstractDynamicDateSource extends AbstractDataSource implements InitializingBean{
	
	public final SwitchDataSource writeSwitchDataSource = new SwitchDataSource();
	public final List<SwitchDataSource> readSwitchDataSources = new ArrayList<SwitchDataSource>();
	
	private DataSource writeDataSource;
	private Map<String, DataSource> readDataSourceMap = new HashMap<String,DataSource>();
    private AtomicInteger counter = new AtomicInteger(1);
    
	@Override
	public void afterPropertiesSet() throws IllegalArgumentException {
		AssertUtils.isNull(writeDataSource,new DynamicDataSourceException("property 'writeDataSource' is required"));
		writeSwitchDataSource.setDataSource(writeDataSource);
		writeSwitchDataSource.setTypeName(DataSourceEnumType.MASTER_WRITE.getDataSourceType());

		//读库未配置默认设置写库为读库
		if (!CollectionUtils.isEmpty(readDataSourceMap)) {
			for (Entry<String, DataSource> entry : readDataSourceMap.entrySet()) {
				SwitchDataSource readSwitchDataSource = new SwitchDataSource();
				readSwitchDataSource.setDataSource(entry.getValue());
				readSwitchDataSource.setTypeName(entry.getKey());
				readSwitchDataSources.add(readSwitchDataSource);
			}
		}else{
			readSwitchDataSources.add(writeSwitchDataSource);
		}
	}

	public SwitchDataSource getWriteSwitchDataSource() {
		return writeSwitchDataSource;
	}

	/**
	 * 当选择为读库的时候， 默认按顺序轮询使用读库 <BR/>
	 * 按照顺序选择读库(该算法都待改进)
	 * @return
	 */
	public SwitchDataSource getReadSwitchDataSource() {
		int index = counter.incrementAndGet() % readSwitchDataSources.size();
		counter.set(index);
		return readSwitchDataSources.get(index);
	}

	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	public void setReadDataSourceMap(Map<String, DataSource> readDataSourceMap) {
		this.readDataSourceMap = readDataSourceMap;
	}

}
