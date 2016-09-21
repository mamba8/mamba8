package com.mamba.framework.boot;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.pool.DruidDataSource;


public class ApplicationDruid {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setFilters("stat");
        dataSource.setConnectionProperties("druid.stat.mergeSql=true");
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }
}
