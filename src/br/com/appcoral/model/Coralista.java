package br.com.appcoral.model;

import java.io.Serializable;

public class Coralista implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCoralista;
	private String nome;
	private String rg;
	private String telefone;
	private String email;
	private String dataNascimento;
	private String nypeVocal;

	private String qrCode;

	private String statusAtivo;

	public Long getIdCoralista() {
		return idCoralista;
	}

	public void setIdCoralista(Long idCoralista) {
		this.idCoralista = idCoralista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNypeVocal() {
		return nypeVocal;
	}

	public String getNypeVocalPorExtenso() {
		if (getNypeVocal() != null) {
			int nype = Integer.parseInt(getNypeVocal());
			switch (nype) {
			case 1:
				return "Soprano";
			case 2:
				return "Contralto";
			case 3:
				return "Tenor";
			case 4:
				return "Baixo";
			}
		}
		return "";
	}

	public void setNypeVocal(String nypeVocal) {
		this.nypeVocal = nypeVocal;
	}

	public String getQrCode() {
		return this.qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getStatusAtivo() {
		return this.statusAtivo;
	}

	public void setStatusAtivo(String ativo) {
		this.statusAtivo = ativo;
	}

	@Override
	public String toString() {
		return this.getNome();
	}

}
