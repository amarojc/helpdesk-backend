package com.amaro.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Chamado;
import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.domain.enums.Perfil;
import com.amaro.helpdesk.domain.enums.Prioridade;
import com.amaro.helpdesk.domain.enums.Status;
import com.amaro.helpdesk.repositories.ChamadoRepository;
import com.amaro.helpdesk.repositories.ClienteRepository;
import com.amaro.helpdesk.repositories.TecnicoRepository;

//Injetar serviços em outras partes do código, sendo gerenciado pelo Spring.
@Service
public class DBService {
	//Ponto de injeção de dependências
	//Spring fica responsável pela criação das instâncias da interface, gerenciar e destruir, no ponto correto.
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Jorge Amaro", "63653230268", "inf.amaro.jc@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Tecnico tec2 = new Tecnico(null, "Tatiana Amaro", "93916862065", "tatin@mail.com", "123");
		tec2.addPerfil(Perfil.TECNICO);
		
		
		Cliente cli1 = new Cliente(null, "Danilo", "80527954780", "danilo@mail.com", "123");
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		
		
		Cliente cli2 = new Cliente(null, "Nickolas Correa", "97431738030", "nick@mail.com", "123");
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Impressora com defeito", "Não está reconhecendo o papel.", null, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Internet", "Internet não está funcionando.", tec1, cli2);
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Computador com problema.", "Máquina travando e reiniciando.", tec2, cli2);
		
		
		Cliente cli3 = new Cliente(null, "Nadir Porto", "31658721047", "nadir@mail.com", "123");
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Planilhas", "Não consta um software de planilha instalado.", null, cli3);

		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
	}
}
