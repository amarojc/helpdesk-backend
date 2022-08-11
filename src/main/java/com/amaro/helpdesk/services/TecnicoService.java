package com.amaro.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.domain.dtos.TecnicoDTO;
import com.amaro.helpdesk.repositories.TecnicoRepository;
import com.amaro.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		//Pode encontrar ou não o objeto no banco.
		Optional<Tecnico> obj = repository.findById(id);
		//Verifica se o objeto existe, senão lança uma exceção e retornar a msg atribuída no ObjectNotFoundException.
		//() função anônima recebendo -> o new ObjectNotFoundException()
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	//As informações que serão gravadas será do tipo Tecnico e não TecnicoDTO
	//Neccesário converter TecnicoDTO para Tecnico
	public Tecnico create(TecnicoDTO objDTO) {
		//Id null por questões de segurança, caso seja passado algum valor para o id, na requisição.
		//Para o BD não entender que seja um update.
		objDTO.setId(null);
		Tecnico newObj = new Tecnico(objDTO);
		//Chamada assincrona -> Primeiro irá salvar no banco e depois retonar o insert com o id do Tecnico criado.
		return repository.save(newObj);
	}
}
