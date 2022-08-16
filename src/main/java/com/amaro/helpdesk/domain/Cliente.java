package com.amaro.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.amaro.helpdesk.domain.dtos.ClienteDTO;
import com.amaro.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cliente extends Pessoa{


	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Chamado> chamados = new ArrayList<>();
	
	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(ClienteDTO cliDTO) {
		this.id = cliDTO.getId();
		this.nome = cliDTO.getNome();
		this.cpf = cliDTO.getCpf();
		this.email = cliDTO.getEmail();
		this.senha = cliDTO.getSenha();
		this.perfis = cliDTO.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
	}
	
	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
}
