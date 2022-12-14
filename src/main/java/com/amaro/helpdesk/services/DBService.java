package com.amaro.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Amaro Cavalcante", "63653230268", "amaro@mail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		
		Tecnico tec2 = new Tecnico(null, "Maria Madalena", "93916862065", "maria@mail.com", encoder.encode("123"));
		tec2.addPerfil(Perfil.TECNICO);
		
		Tecnico tec3 = new Tecnico(null, "Raimundo Nonato", "58561398019", "raimundo@mail.com", encoder.encode("123"));
		tec2.addPerfil(Perfil.TECNICO);
		
		Cliente cli1 = new Cliente(null, "Pedro de Lara", "80527954780", "pedro@mail.com", encoder.encode("123"));
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		
		
		Cliente cli2 = new Cliente(null, "Silvio Santos", "97431738030", "silvio@mail.com", encoder.encode("123"));
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Impressora com defeito", "Não está reconhecendo o papel.", tec3, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Internet", "Internet não está funcionando.", tec1, cli2);
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Computador com problema.", "Máquina travando e reiniciando.", tec2, cli2);
		
		
		Cliente cli3 = new Cliente(null, "Ivo Holanda", "31658721047", "ivo@mail.com", encoder.encode("123"));
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Planilhas", "Não consta um software de planilha instalado.", tec3, cli3);

		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
	}
}
