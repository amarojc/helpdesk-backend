
package com.amaro.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ClienteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotNull(message = "O campo nome é requerido!")
	protected String nome;
	
	//Anotação de CPF para verificar se é um CPF válido...
	@CPF
	@NotNull(message = "O campo CPF é requerido!")
	protected String cpf;
	
	@NotNull(message =  "O campo email é requerido!")
	protected String email;
	
	@NotNull(message = "O campo senha é requerido!")
	protected String senha;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCricacao = LocalDate.now();

	protected Set<Integer> perfis = new HashSet<>();
	
	public ClienteDTO() {
		 super();
		 addPerfil(Perfil.CLIENTE);
	}

	public ClienteDTO(Cliente obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataCricacao() {
		return dataCricacao;
	}

	public void setDataCricacao(LocalDate dataCricacao) {
		this.dataCricacao = dataCricacao;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}
	
}
