package com.amaro.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amaro.helpdesk.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	
	@Autowired
	private DBService dbservice;
	
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String value;
	
	@Bean
	public boolean instanciaDB() {
		//Somente irá executar o dbservice caso as tabelas ainda não tenham sido criadas.
		if(value.equals("create")) {
			this.dbservice.instanciaDB();
		}
		
		return false;
	}
}
