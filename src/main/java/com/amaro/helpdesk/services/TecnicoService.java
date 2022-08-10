package com.amaro.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		//Pode encontrar ou não o objeto no banco.
		Optional<Tecnico> obj = repository.findById(id);
		//Se não encontrar retorna null
		return obj.orElse(null);
	}
}
