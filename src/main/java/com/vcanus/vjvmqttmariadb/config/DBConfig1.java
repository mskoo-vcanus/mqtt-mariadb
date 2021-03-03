package com.vcanus.vjvmqttmariadb.config;

import com.vcanus.vjvmqttmariadb.Mappers.first.Map1;
import com.vcanus.vjvmqttmariadb.Mappers.first.Mapper1;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
//@MapperScan(basePackages="com.vcanus.vjvmqttmariadb")
@MapperScan(value="com.vcanus.vjvmqttmariadb.Mappers.first", annotationClass= Map1.class, sqlSessionFactoryRef = "factory1")
@EnableTransactionManagement
public class DBConfig1 {

    @Primary
    @Bean(name = "datasource1")
    @ConfigurationProperties(prefix = "spring.db1.datasource")
    public DataSource dataSource1(){
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "factory1")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("datasource1")DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "sqlSession1")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sqlSessionTemplate;
    }


}
