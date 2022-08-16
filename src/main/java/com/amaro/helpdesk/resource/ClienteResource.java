package com.amaro.helpdesk.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		//Instancio cliente caso conste o objeto no banco, senão será lançado uma exception em findById.
		Cliente cli = clienteService.findById(id);
		
		//Retorno a resposta para o cliente, convertendo o OBJETO cliente para clienteDTO.
		return ResponseEntity.ok().body(new ClienteDTO(cli));
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = clienteService.findAll();
		
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO){
		Cliente newCli = clienteService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCli.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
