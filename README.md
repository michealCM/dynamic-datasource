功能说明？
1：该jar主要实现对数据库的读写分离处理，支持一写多读（读库数据源可以配置多个）。
2：支持spring零注解配置和xml配置。
3：@Transaction @DataSourceSwitch 注解 和 通过XML配置Spring事务的<tx:advice>事务属性配置拦截处理。
4：由于保证事物的原子性和一致性，所有位于@Transactio注解的方法中都是采用写库操作。

如何在项目中应用？
1：初始化DynamicDataSource注入读库和写库数据源
	xml配置方法：
	<bean id="dynamicDataSource" class="org.dynamic.datasource.core.DynamicDataSource">
		<property name="writeDataSource" ref="writeDataSource" />
		<property name="readDataSourceMap">
			<map>
				<entry key="readDataSource" value-ref="readDataSource" />
				<!-- 可以配置多个读库 -->
				<!-- <entry key="readDataSource1" value-ref="readDataSource1" /> -->
			</map>
		</property>
	</bean>
	
	注解配置方式：
	@Bean
	public DynamicDataSource dynamicDataSource(){
	    DynamicDataSource dynamicDataSource = new DynamicDataSource();
	    dynamicDataSource.setWriteDataSource(writeDataSource());
	    //可以添加多个读库数据源
	    dynamicDataSource.getReadDataSourceMap.put("readDataSource",readDataSource());
	    dynamicDataSource.getReadDataSourceMap.put("readDataSource1",readDataSource1());
	    return dynamicDataSource;
	}
	
2：初始化DataSourceConfig  
    xml配置方式：
    第一种：<bean class="org.dynamic.datasource.DynamicDataSourceConfiguration"/>
    第二种：<bean class="org.dynamic.datasource.DynamicDataSourceConfiguration">
               <property name="aspectJExpressionString" value="execution(* com.test.service.impl.*.*(..))">
           </bean>
    第三种：<bean class="org.dynamic.datasource.DynamicDataSourceConfiguration">
               <property name="patternMatchClassPath" value="service.*">
           </bean>       
     
    注解配置方式：
    @Bean
    public DynamicDataSourceConfiguration dynamicDataSourceConfiguration(){
        return new DynamicDataSourceConfiguration();
    }        
     
    @Bean
    public DynamicDataSourceConfiguration dynamicDataSourceConfiguration(){
        DynamicDataSourceConfiguration dynamicDataSourceConfiguration =  new DynamicDataSourceConfiguration();
        dynamicDataSourceConfiguration.setAspectJExpressionString("execution(* com.test.service.impl.*.*(..))");
        return dynamicDataSourceConfiguration;
    } 
        
    @Bean
    public DynamicDataSourceConfiguration dynamicDataSourceConfiguration(){
        DynamicDataSourceConfiguration dynamicDataSourceConfiguration =  new DynamicDataSourceConfiguration();
        dynamicDataSourceConfiguration.setPatternMatchClassPath("service.impl.*");
        return dynamicDataSourceConfiguration;
    } 
       
              
              
              