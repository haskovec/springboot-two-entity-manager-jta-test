package com.haskovec.springbootdualemjta.config;

import com.haskovec.springbootdualemjta.domain.secondarydb.Secondary;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by jhaskovec on 4/15/16.
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = {Secondary.class},
		entityManagerFactoryRef = "secondaryEntityManagerFactory")
public class SecondaryConfig {
}
