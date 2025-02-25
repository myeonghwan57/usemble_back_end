package com.mycompany.webapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfig {
	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@125.131.208.226:1521:orcl");
		config.setUsername("user_final_team1");
		config.setPassword("oracle");
		config.setMaximumPoolSize(12);
		HikariDataSource hikariDataSource = new HikariDataSource(config);

		return hikariDataSource;
	}
}
