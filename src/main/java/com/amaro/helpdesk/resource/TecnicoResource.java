package com.amaro.helpdesk.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.domain.dtos.TecnicoDTO;
import com.amaro.helpdesk.services.TecnicoService;

//Classe de controle, camada de resource, REST, primeira camada que  aplicação cliente acessa.
//RequestMapping -> Adicionar o endpoint inicial para os serviços. Ou seja, quando for acessar quaisquer
//serviços do técnico será utilizado o /tecnicos.
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService service;
	
	//Endereço do endpoint para ter acesso aos serviços de tecnicos
	//localhost:8080/tecnicos.
	//Trabalhar com api e atua melhor com segurança da informação
	//Requesição do tipo GET
	//Value recebe uma variável de path variable
	//localhost:8080/tecnicos/1
	//Passa como parametro a pathvariable
	//Retornar o TecnicoDTO responsável pela transferência de dados
	@GetMapping(value =  "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
		Tecnico obj = service.findById(id);
		
		//Retornar no corpo da resposta o objeto de TecnicoDTO
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		List<Tecnico> list = service.findAll();
		//Mapeio a list, coletando todos os objetos e atribuo cada um a um obj, adicionando na listDTO 
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	//As informações vêm no corpo da requisição (@RequestBody) deve ser do tipo TecnicoDTO
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
		Tecnico newObj = service.create(objDTO);
		
		//created(URI location) > URI Location -> Ao criar um novo objeto no BD, um novo id é criado.
		//E a aplição cliente fica aguardando a url para ter acesso ao novo obj criado.
		
		//Crio um "link de retorno", host, porta, e caminho de contexto, (fromCurrentContextPath) 
		//em resposta a requisição solicitada.
		//Passadando o caminho de acesso ao objeto na URI (path)
		//Busco o novo id criado (buildAndExpand) e adiciono na URI já convertido para uma URI (toUri) 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newObj.getId()).toUri();
				
		return ResponseEntity.created(uri).build();
	}
	
	//update passa o id e as informações no corpo da requisição 
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id,@Valid  @RequestBody TecnicoDTO objDTO){
		Tecnico obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
