package br.com.appcoral.dto;

import java.io.Serializable;

import br.com.appcoral.model.Coralista;
import br.com.appcoral.util.ItemMensalidadeListView;

public class MensalidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Coralista coralista;
	private ItemMensalidadeListView item;
	private double valor;
	private String mes;
	private String ano;
	private Long id_mensalidade;
	private String situacaoPagamento;

	public MensalidadeDTO(){}
	
	public MensalidadeDTO(Coralista coralistaSelecionado, ItemMensalidadeListView item,String ano, double valor) {
		this.coralista = coralistaSelecionado;
		this.item = item;
		this.valor = valor;
		this.mes = item.getMes();
		this.ano = ano;
	}

	public Coralista getCoralista() {
		return coralista;
	}

	public void setCoralista(Coralista coralista) {
		this.coralista = coralista;
	}

	public ItemMensalidadeListView getItem() {
		return item;
	}

	public void setItem(ItemMensalidadeListView item) {
		this.item = item;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getMes() {
		return mes;
	}

	public String getAno() {
		return ano;
	}
	
	public void setIdMensalidade(Long idMensalidade) {
		this.id_mensalidade = idMensalidade;
	}
	public Long getIdMensalidade() {
		return id_mensalidade;
	}

	public void setSituacaoPagamento(String situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}

	public String getSituacaoPagamento() {
		return situacaoPagamento;
	}
}
