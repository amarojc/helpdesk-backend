package com.amaro.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.amaro.helpdesk.security.JWTAuthenticationFilter;
import com.amaro.helpdesk.security.JWTUtil;


/*
	@EnableWebSecurity já possui a @Configuration
	
	A partir da versão 5.7 o Spring descontinuou o uso do WebSecurityConfigurerAdapter,
	para encorajar uma configuração de segurança baseada em componentes
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 Interface que representa o ambiente no qual nosso aplicativo atual está sendo executado.
	 Podendo ser usado para obter os perfis e propriedades do ambiente do aplicativo. 
	 E como está rodando o ambiente de teste, necessário desabilitar e habilitar o h2 quando estiver no perfil de teste.
	 */
	@Autowired
	private Environment env;
	
	/*
	 Classe que contém o a chave, o tempo de expiração do token e o gerador do token.
	 */
	@Autowired
	private JWTUtil jwtUtil;
	
	/*
	 Injeta as informações da classe UserDetailsServiceImpl que implementa a interface userDetailsService.
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	//Variável para liberar o acesso ao h2-console e tudo que vier após...
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	/*
	Método utilizado para defesa dos endpoints que tenha uma invunerabilidade, configurações de filtros...   
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Verificando qual ambiente a aplicação está rodando, caso teste, habilitar o h2-console, senão desabilitar.
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		/*
		 # .cors() Passando a configuração de cors criada.
		 # .and().csrf().disable() -> Como a aplicação não irá armazenar nada em sessão 
		 será desabilitado o ataque contra Cross-Site Request Forgery (CSRF).
		*/
		http.cors().and().csrf().disable();
		
		//Registrando o filtro de autenticação
		//authenticationManager -> Método da classe que retornar  WebSecurityConfigurerAdapter
		//Adiciono no filtro a resposta de autenticação do usuário podendo ser sucesso ou negado.
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		//Autorizo as requisições de PUBLIC_MATCHERS e de todas as requisições que estão autenticadas (.anyRequest().authenticated()).
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
		
		//# Garantindo que a sessão não será criada para não gerar problemas devido a desabilitação do CSRF.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	
	/*
	 Como será utilizado o método de autenticação do framework, o método configure deverá ser sobreescrito
	 para informar ao framework nossa autenticação.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	/*
	Bean para ser carregado ao subir o projeto.
	Cors utilizado para Liberar os acessos das requisições dos endpoints no front-end. 
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		/*
		 configuration recebe um padrão de implementações para permições de acesso 
		 já implementados de forma explicita.
		 Exemplo como GET, POST, HEAD..
		*/
		 CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		//Métodos para permitir acesso...
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		//Final, não pode ser modificada.
		//CorsConfigurationSource usa padrões de caminho de URL para selecionar o CorsConfiguration para uma solicitação.
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//Registrando uma configuração de Cors(permições)
		//caminho/fontes (/** todas) e métodos permitidos...
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
		
		
	}
	
	/*
	 Bean para criptogravar a senha do usuário no banco.
	 BCrypt que poderá ser injetado em qualquer outra parte do código
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
