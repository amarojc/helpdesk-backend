package com.amaro.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	//Atributo para resgatar o valor em millesegundos definido em aplication.properties.
	@Value("${jwt.expiration}")
	private Long expiration;
	//Atributo para resgatar o valor da chave secreta definida em aplication.properties.
	@Value("${jwt.secret}")
	private String secret;
	/*
	 Método que irá gerar o token
	 */
	public String generateToken(String email) {
		//setSubject -> Define o valor de assunto das revendicações do token
		//setExpiration -> Data de expiração do token, devendo ser a data atual + expiration.
		//signWith -> Tipo do algoritmo (jwt.io) + chave secreta (secret) de bytes
		//compact -> Utilizado para compactar o corpo do jwt deixando api mais performática.
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
