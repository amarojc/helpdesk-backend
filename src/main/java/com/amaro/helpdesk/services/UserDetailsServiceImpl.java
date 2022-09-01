package com.amaro.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Pessoa;
import com.amaro.helpdesk.repositories.PessoaRepository;
import com.amaro.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	Carrega o usuário pelo seu email
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Pessoa> user = pessoaRepository.findByEmail(email);
		
		if(user.isPresent()) {
			//Contrato retorna as informações do usuário caso seja encontrato no banco de dados
			return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
		}
		
		throw new UsernameNotFoundException(email);
	}

}
