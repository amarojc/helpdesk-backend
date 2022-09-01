package com.amaro.helpdesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.amaro.helpdesk.domain.enums.Perfil;

/*
Classe para implementar os contratos do Spring Security

Obs: O retorno de cada método depende da necessidade do cliente, ou seja, da regra de negócio da aplicação.
 */
public class UserSS implements UserDetails{

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		//Mapear cada perfil x, passando todos os perfis que terão permissão de acessso, coletando tudo e convertendo para o tipo set.
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	//Conta não expirada?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//Conta não bloqueada?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//Senhas não expiradas?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//Está habilitado?
	@Override
	public boolean isEnabled() {
		return true;
	}

}
