package com.amaro.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Pessoa;
import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.domain.dtos.TecnicoDTO;
import com.amaro.helpdesk.repositories.PessoaRepository;
import com.amaro.helpdesk.repositories.TecnicoRepository;
import com.amaro.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.amaro.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
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
		
		//Método para verificar se CPF já está cadastrado no banco
		validaPorCpfEEmail(objDTO);
		
		Tecnico newObj = new Tecnico(objDTO);
		//Chamada assincrona -> Primeiro irá salvar no banco e depois retonar o insert com o id do Tecnico criado.
		return repository.save(newObj);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		
		/* Se o objeto instanciado for presente, significa que ele existe.
		   E se obj.get.getId for diferente de objDTO.getId
		   Lança uma exceção... 
		 */
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema! " + objDTO.getCpf());
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
			throw new DataIntegrityViolationException("Email já cadastrado no sistema! " + objDTO.getEmail());
		}
		
	}
}
