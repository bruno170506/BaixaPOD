package br.com.baixapod.model;

public class PesquisaPOD {

	private String n_hawb;
	private String descricao;
	
	public String getN_hawb() {
		return n_hawb;
	}
	public void setN_hawb(String n_hawb) {
		this.n_hawb = n_hawb;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
