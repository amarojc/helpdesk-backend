package com.amaro.helpdesk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amaro.helpdesk.domain.Pessoa;

//Passagem uma classe e um tipo primitivo do objeto identificador da nossa classe....
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	Optional<Pessoa> findByCpf(String cpf);
	Optional<Pessoa> findByEmail(String email);
}
