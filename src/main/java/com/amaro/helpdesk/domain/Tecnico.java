package com.amaro.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

import com.amaro.helpdesk.domain.dtos.TecnicoDTO;
import com.amaro.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tecnico extends Pessoa{
	
	private static final long serialVersionUID = 1L;
	
	//Para evitar que ocarra uma serialização e um loop infinito
	//Retornar apenas as informações do tecnico ignorando os chamados
	@JsonIgnore
	@OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();
	
	
	public Tecnico() {
		super();
		addPerfil(Perfil.TECNICO);
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.TECNICO);
	}

	//Método para converter TecnicoDTO p/ Tecnico
	public Tecnico(TecnicoDTO  obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}
	
	
	
	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}


}
