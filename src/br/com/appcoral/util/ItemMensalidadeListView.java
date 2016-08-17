package br.com.appcoral.util;

import java.io.Serializable;

public class ItemMensalidadeListView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mes;
	private int iconeResourceId;

	public ItemMensalidadeListView() {
	}

	public ItemMensalidadeListView(String texto, int iconeRid) {
		this.mes = texto;
		this.iconeResourceId = iconeRid;
	}

	public int getIconeResourceId() {
		return iconeResourceId;
	}
	
	public void setIconeResourceId(int iconeRid) {
		this.iconeResourceId = iconeRid;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
}