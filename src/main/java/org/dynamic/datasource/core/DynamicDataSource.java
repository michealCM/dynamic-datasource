package org.dynamic.datasource.core;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dynamic.datasource.core.context.SwitchDataSourceContext;
import org.springframework.jdbc.datasource.AbstractDataSource;

/**
 * 自定义数据源
 * {@link AbstractDataSource}
 * 
 * @author micheal
 * @date 2014-8-19 23:31:19 
 */
public class DynamicDataSource extends AbstractDynamicDateSource {
	
	   @Inject
	   private SwitchDataSourceContext switchDataSourceContext;
	   
	  @Override
	    public Connection getConnection() throws SQLException {
	        return this.switchDataSourceContext.getSwitchDataSource().getDataSource().getConnection();
	    }
	    
	    @Override
	    public Connection getConnection(String username, String password) throws SQLException {
	        return this.switchDataSourceContext.getSwitchDataSource().getDataSource().getConnection(username, password);
	    }
	    
}
