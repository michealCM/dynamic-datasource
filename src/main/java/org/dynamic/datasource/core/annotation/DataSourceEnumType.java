package org.dynamic.datasource.core.annotation;

/**
 * 数据源类型（主库=》写库；从库=》读库）
 *
 * @date 2018-11-14 21:08:40
 */
public enum DataSourceEnumType{

    MASTER_WRITE("write"),
    SLAVE_READ("read");

    private String dataSourceType;

    private DataSourceEnumType(String dataSourceType){
        this.dataSourceType = dataSourceType;
    }

    public String getDataSourceType(){
        return dataSourceType;
    }

}
