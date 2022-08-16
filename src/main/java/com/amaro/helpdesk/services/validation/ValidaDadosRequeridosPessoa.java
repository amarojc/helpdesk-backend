package com.amaro.helpdesk.services.validation;

import java.util.Optional;

import com.amaro.helpdesk.domain.Pessoa;
import com.amaro.helpdesk.repositories.PessoaRepository;
import com.amaro.helpdesk.services.exceptions.DataIntegrityViolationException;

public class ValidaDadosRequeridosPessoa {
	
	public void validaPorCpfEEmail(Object objDTO, PessoaRepository objRepor) {
		Pessoa pDTO = (Pessoa) objDTO;
		Optional<Pessoa> obj = objRepor.findByCpf(pDTO.getCpf());
		
		//Verifica se o obj (pessoa) existe E se o id de pessoa é igual ao id de pessoaDTO.
		if(obj.isPresent() && obj.get().getId() != pDTO.getId()) {
			throw new DataIntegrityViolationException("CPF informado já cadastrado no sistema!");
		}
		
		obj = objRepor.findByEmail(pDTO.getEmail());
		
		if(obj.isPresent() && obj.get().getId() != pDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail informada já cadastrado no sistema!");
		}
	}

}
