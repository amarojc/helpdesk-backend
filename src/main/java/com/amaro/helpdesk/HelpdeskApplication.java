package com.amaro.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amaro.helpdesk.domain.Chamado;
import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.domain.enums.Perfil;
import com.amaro.helpdesk.domain.enums.Prioridade;
import com.amaro.helpdesk.domain.enums.Status;
import com.amaro.helpdesk.repositories.ChamadoRepository;
import com.amaro.helpdesk.repositories.ClienteRepository;
import com.amaro.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{

	//Ponto de injeção de dependências
	//Spring fica responsável pela criação das instâncias da interface, gerenciar e destruir no ponto correto.
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	//Será executado sempre que startar aplicação. 
	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Jorge Amaro", "63653230268", "inf.amaro.jc@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Danilo", "80527954780", "danilo@mail.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		

		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
		
	}

}
