package com.amaro.helpdesk.domain.enums;

public enum Perfil {
	//Importante número para não gerar problemas futuros, caso um novo
	// desenvolvedor for fazer manutenção no projeto e acrescentar mais perfis 
	
	ADMIN(0, "ROLE_ADMIN"), 
	CLIENTE(1,"ROLE_CLIENTE"), 
	TECNICO(2,"ROLE_TECNICO");
	
	private Integer codigo;
	private String descricao;
	
	private Perfil() {
	}

	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	//Static para não precisar instanciar o método em outra parte do projeto.
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		//Para cada perfil...
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
				
		}
		throw new IllegalArgumentException("Perfil inválido");
	}
}
