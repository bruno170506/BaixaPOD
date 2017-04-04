package br.com.baixapod.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import br.com.baixapod.dto.CoordenadaDTO;

@SuppressLint("SimpleDateFormat")
public class MovimentoPOD {

	private String dt_entrega;
	private String hr_entrega;
	private String ocorrencia;
	private String n_tentativas;
	private String n_hawb;
	private String sucesso;
	private String longitude;
	private String latitude;
	private String observacao;
	

	public MovimentoPOD(){}
	
	public MovimentoPOD(ItemPOD pod, CoordenadaDTO coordenadaDTO) {
		SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
		Date dataHoraAtual = new Date();
		this.dt_entrega = formatData.format(dataHoraAtual);
		this.hr_entrega = formatHora.format(dataHoraAtual);
		this.ocorrencia = pod.getOcorrencia();
		this.n_tentativas = pod.getN_tentativas();
		this.n_hawb = pod.getN_hawb();
		this.sucesso = pod.getSucesso();
		if(coordenadaDTO!=null){
			this.longitude = coordenadaDTO.getLongitude();
			this.latitude = coordenadaDTO.getLatitude();
			this.observacao = "";
		}else{
			this.longitude = "";
			this.latitude = "";
			this.observacao = "GPS DESABILITADO";
		}
	}

	public JSONObject getJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("dt_entrega", getDt_entrega());
			obj.put("hr_entrega", getHr_entrega());
			obj.put("ocorrencia", getOcorrencia());
			obj.put("n_tentativas", getN_tentativas());
			obj.put("n_hawb", getN_hawb());
			obj.put("sucesso", getSucesso());
			obj.put("longitude", getLongitude());
			obj.put("latitude", getLatitude());
			obj.put("observacao", getObservacao());
			
		} catch (JSONException e) {}
		return obj;
	}

	public String getDt_entrega() {
		return dt_entrega;
	}

	public void setDt_entrega(String dt_entrega) {
		this.dt_entrega = dt_entrega;
	}

	public String getHr_entrega() {
		return hr_entrega;
	}

	public void setHr_entrega(String hr_entrega) {
		this.hr_entrega = hr_entrega;
	}

	public String getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String getN_tentativas() {
		return n_tentativas;
	}

	public void setN_tentativas(String n_tentativas) {
		this.n_tentativas = n_tentativas;
	}

	public String getN_hawb() {
		return n_hawb;
	}

	public void setN_hawb(String n_hawb) {
		this.n_hawb = n_hawb;
	}
	
	public String getSucesso() {
		return sucesso;
	}
	public void setSucesso(String sucesso) {
		this.sucesso = sucesso;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
