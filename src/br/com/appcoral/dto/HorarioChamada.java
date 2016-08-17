package br.com.appcoral.dto;

import java.io.Serializable;

public class HorarioChamada implements Serializable{

	private static final long serialVersionUID = 1L;
	private String horarioChamada;
	private String minutosTolerancia;

	public String getHorarioChamada() {
		return horarioChamada;
	}
	public void setHorarioChamada(String horarioChamada) {
		this.horarioChamada = horarioChamada;
	}

	public String getMinutosTolerancia() {
		return minutosTolerancia;
	}
	public void setMinutosTolerancia(String minutosTolerancia) {
		this.minutosTolerancia = minutosTolerancia;
	}

	
}
