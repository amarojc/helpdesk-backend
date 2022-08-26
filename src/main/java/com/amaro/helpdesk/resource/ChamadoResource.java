package com.amaro.helpdesk.resource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amaro.helpdesk.domain.Chamado;
import com.amaro.helpdesk.domain.dtos.ChamadoDTO;
import com.amaro.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado obj = chamadoService.findById(id);
		return ResponseEntity.ok(new ChamadoDTO(obj));
	}
	
	//Método retorna uma lista de chamados do tipo DTO para a requisição do cliente
	//Anotação da requisição do tipo get
	//Lista de Chamados recebe todos os chamados através do método findAll service 
	//Converto a lista de chamados em um stream e chamo o método map para converter 
	// a lista de chamados p/ chamadosDTO
	//Retorno a lista de chamados para a requisição do cliente
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		List<Chamado> list = chamadoService.findAll();
		
		/*
		 ParallelStream irá decompor as ações em várias subtarefas,
		 e as operações serão processadas em paralelo, explorando os recursos 
		 oferecidos pelos diversos núcleos do processador, um bom recurso 
		 para ser utilizado na manipulação de grande quantidade de dados.
		*/
		List<ChamadoDTO> listDTO = list.parallelStream()
				.map(obj ->  new ChamadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO); 
	}

}
