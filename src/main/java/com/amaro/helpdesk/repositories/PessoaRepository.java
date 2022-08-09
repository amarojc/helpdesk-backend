package com.amaro.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amaro.helpdesk.domain.Pessoa;

//Passagem uma classe e um tipo primitivo do objeto identificador da nossa classe....
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
