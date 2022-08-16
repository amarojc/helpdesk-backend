package com.amaro.helpdesk.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.domain.dtos.ClienteDTO;
import com.amaro.helpdesk.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	//Injection
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
		//Instancio cliente caso o conste o objeto no banco, senão será lançado uma exception em findById.
		Cliente cli = clienteService.findById(id);
		
		//Retorno a resposta para o cliente, convertendo o OBJETO cliente para clienteDTO.
		return ResponseEntity.ok().body(new ClienteDTO(cli));
	}
	

}
