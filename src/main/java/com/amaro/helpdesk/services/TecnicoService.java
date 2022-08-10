package com.amaro.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Tecnico;
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
}
