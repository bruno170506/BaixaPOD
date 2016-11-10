package br.com.baixapod.model;

public class Pessoa {

	private String usuario;
	private String senha;
	private String matricula;
	private boolean conectado;

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public void setConectado(boolean b) {
		this.conectado = b;
	}
	public boolean isConectado() {
		return conectado;
	}
	
	
}

