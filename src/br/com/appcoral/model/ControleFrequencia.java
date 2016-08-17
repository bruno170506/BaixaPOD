package br.com.appcoral.model;

import java.io.Serializable;

public class ControleFrequencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idFrequencia;
	private Long idCoralista;
	private String dataHoraFrequencia;
	
	public Long getIdFrequencia() {
		return idFrequencia;
	}
	public void setIdFrequencia(Long idFrequencia) {
		this.idFrequencia = idFrequencia;
	}
	public Long getIdCoralista() {
		return idCoralista;
	}
	public void setIdCoralista(Long idCoralista) {
		this.idCoralista = idCoralista;
	}
	public String getDataHoraFrequencia() {
		return dataHoraFrequencia;
	}
	public void setDataHoraFrequencia(String dataHoraFrequencia) {
		this.dataHoraFrequencia = dataHoraFrequencia;
	}

	
}
