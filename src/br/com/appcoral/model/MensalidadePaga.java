package br.com.appcoral.model;

import java.io.Serializable;

public class MensalidadePaga implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idMensalidade;
	private Long idCoralista;
	private String ano;
	private String mes;
	private Double valorPago;
	private String situacaoPagamento;
	private String dataPagamento;

	private boolean descontoTerceiroFamiliar;
	public Long getIdMensalidade() {
		return idMensalidade;
	}
	public void setIdMensalidade(Long idMensalidade) {
		this.idMensalidade = idMensalidade;
	}
	public Long getIdCoralista() {
		return idCoralista;
	}
	public void setIdCoralista(Long idCoralista) {
		this.idCoralista = idCoralista;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Double getValorPago() {
		return valorPago;
	}
	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}
	
	public String getSituacaoPagamento() {
		return situacaoPagamento;
	}
	public void setSituacaoPagamento(String situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}
	
	public String getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	@Override
	public String toString() {
		return getValorPago()+" | "+getDataPagamento();
	}
	public void setDescontoTerceiroFamiliar(boolean checked) {
		this.descontoTerceiroFamiliar = checked;
	}
	public boolean isDescontoTerceiroFamiliar() {
		return descontoTerceiroFamiliar;
	}
	
}
