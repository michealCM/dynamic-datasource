package org.dynamic.datasource.core.context;

import javax.sql.DataSource;

public final class SwitchDataSource{

	//数据源类型（读库，写库）
	private String typeName;

	//具体的数据源
	private DataSource dataSource;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
