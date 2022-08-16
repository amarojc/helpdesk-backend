package com.amaro.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.repositories.ClienteRepository;
import com.amaro.helpdesk.repositories.PessoaRepository;
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
	
	

}
