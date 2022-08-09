package com.amaro.helpdesk.domain.enums;

public enum Prioridade {
	ADM(0, "BAIXA"), 
	CLIENTE(1,"MEDIA"), 
	TECNICO(2,"ALTA");
	
	private Integer codigo;
	private String descricao;
	
	private Prioridade() {
	}

	private Prioridade(Integer codigo, String descricao) {
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
	
	public static Prioridade toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		
		for(Prioridade x : Prioridade.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
				
		}
		throw new IllegalArgumentException("Prioridade inválida");
	}
}
