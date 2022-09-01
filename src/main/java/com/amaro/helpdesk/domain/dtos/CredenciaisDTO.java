package com.amaro.helpdesk.domain.dtos;

//Não é necessário o serializar, não irá trafegar em rede,
// apenas realizar a conversão de usuário e senha vindo da requisição do login.  
public class CredenciaisDTO{
	
	private String email;
	private String senha;
	
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
	
	

}
