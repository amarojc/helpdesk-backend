package com.amaro.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.amaro.helpdesk.domain.dtos.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.html
Processa um envio de formulário de autenticação.
Chamado AuthenticationProcessingFilter do Spring Security 3.0.
Os formulários de login devem apresentar dois parâmetros para este filtro: 
nome de usuário e senha. Os nomes de parâmetros padrão a serem usados ​​estão contidos
nos campos estáticos SPRING_SECURITY_FORM_USERNAME_KEYe SPRING_SECURITY_FORM_PASSWORD_KEY. 
Os nomes dos parâmetros também podem ser alterados definindo as propriedades usernameParametere passwordParameter.

Este filtro por padrão responde à URL /login.
*/
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	/*
	 Principal interface de estratégica para autenticação.
     A implementação deve seguir um destes procedimentos:

	 Retornar um token de autenticação preenchido para o usuário autenticado, indicando uma autenticação bem-sucedida
	 Retorna null, indicando que o processo de autenticação ainda está em andamento.
	 Antes de retornar, a implementação deve realizar qualquer trabalho adicional necessário para concluir o processo.
	 Lançar um AuthenticationException se o processo de autenticação falhar

	 */
	private AuthenticationManager authenticationManager;
	
	// Classe contém o método gerador de token implentado.
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}



	//att ctrl + espaço
	//Método de tentativa de autenticação
	//Pegar os valores passados pela requisição POST de login e converter em credenciais do tipo dto.
	//Instanciar um obj do tipo UsernamePasswordToken e passa o objeto como parâmetro pro método authenticate
	// localizado dentro do authenticationManager
	// authenticationManager -> Realizar por debaixo dos panos o processo de tentativa de autenticar o usuário,
	// utilizando as classes criadas UserSS, UserDetailsServiceIm, conforme a necessida dessa api.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException{
		
		try {
			//request.getInputStream -> Pego o valor do corpo da requisição como dados binários, utilizando um servlet inputstream.
			//Informo de qual classe será realizada a leitura dos valores.
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			
			//Informo os dados para autenticação do token 
			UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
			
			//Retorna uma resposta da autenticação.
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			return authentication;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	//suc ctrl + espaço
	//Método de autenticação com sucesso
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		//Resgato o resultado da autenticação
		//Gero o token 
		//Passo a resposta para requisição	
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		
		//access-control-expose-headers -> Utilizado para pegar o token na requisição.
		response.setHeader("access-control-expose-headers ", "Authorization");
		
		//Athorization para pegar o valor do token, que devera ser concatenado com o termo Bearer
		response.setHeader("Authorization", "Bearer " + token);
		
	}
	
	
	//un ctrl + espaço
	//Método de autenticação negado
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	}



	private CharSequence json() {
		long date = new Date().getTime();
		
		return "{"
				+ "\"timestamp\":" + date + ", "
				+ "\"status\": 401, "
				+ "\"error\": \"Não autorizado\", "
				+ "\"message\": \"Email ou senha inválidos\", "
				+ "\"path\": \"/login"
				+"}";
	}
}
