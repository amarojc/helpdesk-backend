package com.amaro.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amaro.helpdesk.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	//instanciando a classe DBService...
	@Autowired
	private DBService dbservice;
	
	@Bean
	public void instanciaDB() {
		this.dbservice.instanciaDB();
	}
}
