package com.amaro.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.domain.Pessoa;
import com.amaro.helpdesk.domain.dtos.ClienteDTO;
import com.amaro.helpdesk.repositories.ClienteRepository;
import com.amaro.helpdesk.repositories.PessoaRepository;
import com.amaro.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.amaro.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Integer id){
		Optional<Cliente> cli = clienteRepository.findById(id);
		
		//orlElseThrow -> verifica se o cliente existe, caso não gera uma exception...
		//Método anônimo recebe uma msg de erro caso objeto não encontrado.
		return cli.orElseThrow(() -> new ObjectNotFoundException("Cliente não localizado!"));
	}
	
	//Buscando todos os cliente no BD.
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
	
	//Criando um novo cliente
	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		
		validaPorCpfEEmail(objDTO);
		
		Cliente newCli = new Cliente(objDTO);
		return clienteRepository.save(newCli);
	}
	
	
	//Atualizando um novo cliente
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		
		Cliente oldCli = findById(id);
	
		validaPorCpfEEmail(objDTO);
		
		oldCli = new Cliente(objDTO);
		
		return clienteRepository.save(oldCli);
		
	}
	
	//Delete cliente
	public void delete(Integer id) {
		Cliente cli = findById(id);
		if(cli.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui chamados e não poderá ser deletado!");
		}
		clienteRepository.deleteById(id);
	}
	
	
	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		Optional<Pessoa> p = pessoaRepository.findByCpf(objDTO.getCpf());
		
		if(p.isPresent() && p.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		p = pessoaRepository.findByEmail(objDTO.getEmail());
		
		if(p.isPresent() && p.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}
	}
	
}
