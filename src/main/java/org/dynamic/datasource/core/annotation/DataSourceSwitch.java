package org.dynamic.datasource.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义数据库选择注解标签,凡是由该标签注解的方法,或者class都是默认选择主读库来进行数据操作 <BR/>
 *
 * 使用方式如：{@DataSourceSwitch(value=DataSourceEnumType.MASTER_WRITE)}添加到method或者class的顶部就可以了
 * {@link DataSourceEnumType}
 *
 * @author micheal
 * @date 2014-8-21 20:47:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface DataSourceSwitch {

    public DataSourceEnumType value() default DataSourceEnumType.MASTER_WRITE;

}

