package com.haskovec.springbootdualemjta.config;

import com.haskovec.springbootdualemjta.domain.primarydb.Example;
import com.haskovec.springbootdualemjta.domain.secondarydb.Secondary;
import org.apache.commons.dbcp2.managed.BasicManagedDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

/**
 * Created by jhaskovec on 4/15/16.
 */
@Configuration
public class EntityManagerConfig {
	@Autowired
	private TransactionManager tm;

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.primary")
	DataSource primaryDataSource() {
		BasicManagedDataSource ds = new BasicManagedDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE");
		ds.setUsername("sa");
		ds.setPassword("saPassword");
		ds.setTransactionManager(tm);

		return ds;
	}

	@Bean
	@ConfigurationProperties(prefix = "datasource.secondary")
	DataSource secondaryDataSource() {
		BasicManagedDataSource ds = new BasicManagedDataSource();
		ds.setDriverClassName("org.hsqldb.jdbcDriver");
		ds.setUrl("jdbc:hsqldb:mem:/test2");
		ds.setUsername("sa");
		ds.setPassword("");
		ds.setTransactionManager(tm);

		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(primaryDataSource())
				.packages(Example.class)
				.persistenceUnit("primary")
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(secondaryDataSource())
				.packages(Secondary.class)
				.persistenceUnit("secondary")
				.build();
	}
}
