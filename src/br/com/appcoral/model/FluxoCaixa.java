package br.com.appcoral.model;

import java.io.Serializable;

public class FluxoCaixa implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FLUXO_TIPO_ENTRADA = "fluxoTipoEntrada";
	public static final String FLUXO_TIPO_SAIDA = "fluxoTipoSaida";
	
	private Long idFluxoCaixa;
	private String descricao;
	private String tipoFluxo;
	private Double valor;
	
	public Long getIdFluxoCaixa() {
		return idFluxoCaixa;
	}
	public void setIdFluxoCaixa(Long idFluxoCaixa) {
		this.idFluxoCaixa = idFluxoCaixa;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoFluxo() {
		return tipoFluxo;
	}
	public void setTipoFluxo(String tipoFluxo) {
		this.tipoFluxo = tipoFluxo;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return getDescricao();
	}
	
}
