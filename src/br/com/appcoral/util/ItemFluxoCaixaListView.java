package br.com.appcoral.util;

import java.io.Serializable;

public class ItemFluxoCaixaListView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private int icone;

	public ItemFluxoCaixaListView() {}

	public ItemFluxoCaixaListView(String descricao, int icone) {
		this.descricao = descricao;
		this.icone = icone;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIcone() {
		return icone;
	}

	public void setIcone(int icone) {
		this.icone = icone;
	}
	
	
}