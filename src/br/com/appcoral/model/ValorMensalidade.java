package br.com.appcoral.model;

import java.io.Serializable;

public class ValorMensalidade implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long idValorMensalidade;
	private double valorMensalidade;
	private double valorAcrescimo;
	
	public ValorMensalidade() {
		this.idValorMensalidade = -1L;
	}
	
	public Long getIdValorMensalidade() {
		return idValorMensalidade;
	}
	public void setIdValorMensalidade(Long idValorMensalidade) {
		this.idValorMensalidade = idValorMensalidade;
	}
	public double getValorMensalidade() {
		return valorMensalidade;
	}
	public void setValorMensalidade(Double valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}
	public double getValorAcrescimo() {
		return valorAcrescimo;
	}
	public void setValorAcrescimo(double valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}
	
}
