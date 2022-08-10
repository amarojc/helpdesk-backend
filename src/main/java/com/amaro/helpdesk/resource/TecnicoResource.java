package com.amaro.helpdesk.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.services.TecnicoService;

//Classe de controle, camada de resource, REST, primeira camada que a aplicação cliente acessa.
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
	@GetMapping(value =  "/{id}")
	public ResponseEntity<Tecnico> findById(@PathVariable Integer id){
		Tecnico obj = service.findById(id);
		
		//Retornar no corpo da resposta o objeto
		return ResponseEntity.ok().body(obj);
	}
}
